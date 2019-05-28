package magpiebridge.vault.service;

import java.security.Key;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * The currently logged in user which saved in the session.
 * 
 *
 */
public class LoggedInUser extends User {
	
	/**
   * 
   */
  private static final long serialVersionUID = 7358707370464768734L;
  private Key userKey; 

	public LoggedInUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}
	
	public void setUserKey(Key userKey) {
		this.userKey = userKey;
	}
	
	public Key userKey() {
		return userKey;
	}
	

}
