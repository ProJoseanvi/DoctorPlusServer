package com.doctorplus.security.config;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public final class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	protected TokenAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);
	}

	private static final String BEARER = "Bearer";
	private static final String AUTHORIZATION = "Authorization";
	


	@Override
	public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) {
		String param = request.getHeader(AUTHORIZATION);
		if (StringUtils.isEmpty(param)) {
			param = request.getParameter("t");
		}

		if (StringUtils.isEmpty(param)) {
			new BadCredentialsException("Missing Authentication Token");
		}

		String token = param.substring(7);

		final Authentication auth = new UsernamePasswordAuthenticationToken(token, token);
		return getAuthenticationManager().authenticate(auth);
	}

	@Override
	protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain chain, final Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);
		chain.doFilter(request, response);
	}

}
