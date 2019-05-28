package magpiebridge.vault.service;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;


public final class CryptoUtils {

	
	public static String encrypt(String toEncrypt, Key key) {
		return encrypt(toEncrypt.getBytes(), key);
	}
	
	public static String encrypt(byte[] toEncrypt, Key key) {
		 try {
	         Cipher c = Cipher.getInstance("AES");
	         c.init(Cipher.ENCRYPT_MODE, key);
	         return Base64.getEncoder().encodeToString(c.doFinal(toEncrypt));
	      } catch (Exception e) {
	         throw new RuntimeException(e);
	      }
	}
	
	public static String decrypt(String toDecrypt, Key key) {
		return decrypt(toDecrypt.getBytes(), key);
	}
		
	public static String decrypt(byte[] toDecrypt, Key key) {
		return new String(decryptToBytes(toDecrypt, key));
	}

	public static byte[] decryptToBytes(byte[] toDecrypt, Key key) {
		try {
      Cipher c = Cipher.getInstance("AES");
      c.init(Cipher.DECRYPT_MODE, key);
			return c.doFinal(Base64.getDecoder().decode(toDecrypt));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
