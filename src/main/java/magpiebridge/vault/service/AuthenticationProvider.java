package magpiebridge.vault.service;

import java.security.Key;
import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * The class provides authentication for user login.
 * 
 *
 */
@Component
public class AuthenticationProvider extends DaoAuthenticationProvider {

  public AuthenticationProvider(UserDetailsService userDetailsService) {
    setUserDetailsService(userDetailsService);
  }

  /**
   * Performs authentication.
   *
   * @param authentication
   *          the authentication request
   * @return the authenticated token
   * @throws AuthenticationException
   *           the authentication exception
   */
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
    UserDetails user = retrieveUser(authentication.getName(), token);

    if (token.getCredentials() != null) {
      Key masterKey = UserDetailsServiceImpl.getMasterKey(user.getPassword(), token.getCredentials().toString());
      if (((UserDetailsServiceImpl) getUserDetailsService()).checkPassword(authentication.getName(), masterKey)) {
        if (user instanceof LoggedInUser) {
          Key userKey = UserDetailsServiceImpl.decryptUserEncryptionKey(user.getPassword(), token.getCredentials().toString());
          ((LoggedInUser) user).setUserKey(userKey);
        }
        // create a new token that is marked as "authenticated" by the constructor
        token = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        token.setDetails(authentication.getDetails());
        return token;
      }
    }
    throw new AuthenticationCredentialsNotFoundException("");
  }

}
