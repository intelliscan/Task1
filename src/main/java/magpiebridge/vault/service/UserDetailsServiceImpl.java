package magpiebridge.vault.service;

import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import magpiebridge.vault.jpa.model.User;
import magpiebridge.vault.jpa.repository.UserRepository;

/**
 * The Class UserDetailsServiceImpl.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private static int count = 20;
  
  /** The user repo. */
  @Autowired
  private UserRepository userRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepo.findByUserName(username);
    if (user == null) {
      throw new UsernameNotFoundException("");
    }
    UserDetails details = new LoggedInUser(username, user.getSalt() + ":" + user.encryptionKey(), true, true, true, true,
        new ArrayList<>());
    return details;
  }

  /**
   * Create a user and use the masterpassword to create a encryption key .
   *
   * @param userName the user name
   * @param masterPassword the master password
   * @return the user
   */
  public User createUser(String userName, String masterPassword) {
    User user = new User();
    user.setUserName(userName);

    // generate random salt
    Random random = new Random();
    byte[] salt = new byte[32];
    random.nextBytes(salt);
    user.setSalt(Base64.getEncoder().encodeToString(salt));

    // generate random encryption key which will be used for encrypting passwords.
    byte[] encryptionKey = new byte[32];
    random.nextBytes(encryptionKey);

    Key masterKey = createKey(masterPassword, salt);
    user.setEncryptionKey(CryptoUtils.encrypt(encryptionKey, masterKey));
    user.setSaltEncrypted(CryptoUtils.encrypt(salt, masterKey));
    return userRepo.save(user);

  }

  /**
   * User the encryption key to test if the salt can be decrypted.
   *
   * @param userName
   *          the user name
   * @param masterKey
   *          the encryption key
   * @return true, if successful
   */
  public boolean checkPassword(String userName, Key masterKey) {
    User user = userRepo.findByUserName(userName);
    byte[] salt = CryptoUtils.decryptToBytes(user.saltEncrypted().getBytes(), masterKey);
    return user.getSalt().equals(Base64.getEncoder().encodeToString(salt));
  }

  /**
   * Creates an encryption key. 
   *
   * @param text the text
   * @param salt the salt
   * @return the key
   */
   
   

  public static Key createKey(String text, byte[] salt) {
    try {
      char[] textCharArray = text.toCharArray();
      PBEKeySpec pbe = new PBEKeySpec(textCharArray, salt, count, 128);
      SecretKeyFactory skf;
      skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
      SecretKey key=skf.generateSecret(pbe);
      return new SecretKeySpec(key.getEncoded(), "AES");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  
  /**
   * Gets the master key which is used to encrypt the {@link User#encryptionKey()}.
   *
   * @param saltAndKey the salt and key
   * @param masterpassword the masterpassword
   * @return the master key
   */
  public static Key getMasterKey(String saltAndKey, String masterpassword) {
    String salt = saltAndKey.split(":")[0];
    Key masterKey = createKey(masterpassword, Base64.getDecoder().decode(salt));
    return masterKey;
  }

  /**
   * Decrypt the {@link User#encryptionKey()}.
   *
   * @param saltAndKey the salt and key
   * @param masterpassword the masterpassword
   * @return the key
   */
  public static Key decryptUserEncryptionKey(String saltAndKey, String masterpassword) {
    String salt = saltAndKey.split(":")[0];
    String encrytionKey = saltAndKey.split(":")[1];
    Key masterKey = getMasterKey(saltAndKey, masterpassword);
    String decryptedEncrytionKey = CryptoUtils.decrypt(encrytionKey.getBytes(), masterKey);
    return createKey(decryptedEncrytionKey, salt.getBytes());
  }

  /**
   * Gets the current {@link LoggedInUser#userKey()} and it is the same as decrypted {@link User#encryptionKey()}.
   *
   * @return the current user key
   */
  public static Key getCurrentUserKey() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return ((LoggedInUser) auth.getPrincipal()).userKey();
  }

}
