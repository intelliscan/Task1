package magpiebridge.vault.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LoginUtils {

	//org.springframework.security.authentication.AuthenticationManager
	@Autowired
	private AuthenticationManager authenticationManager;

	public void loginDirectly(String username, String password) {
	    UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(username, password);
	    Authentication authenticatedUser = authenticationManager.authenticate(loginToken);
	    SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
	    }
	}
