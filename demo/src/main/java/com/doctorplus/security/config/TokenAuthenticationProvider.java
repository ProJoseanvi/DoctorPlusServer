package com.doctorplus.security.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.doctorplus.service.UserAuthenticationService;

public final class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	
	private UserAuthenticationService auth;
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		// TODO Auto-generated method stub

	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		String token = (String) authentication.getCredentials();
		
		return auth.findByToken(token);
	}

}
