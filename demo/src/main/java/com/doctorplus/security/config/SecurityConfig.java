package com.doctorplus.security.config;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.doctorplus.service.JwtUserDetailsService;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfig {

	private static final Logger logger = LogManager.getLogger(SecurityConfig.class);

	// private static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(new
	// AntPathRequestMatcher("/public/**"));
	// private static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(new
	// AntPathRequestMatcher("/greeting"));
	// private static final RequestMatcher PROTECTED_URLS = new
	// NegatedRequestMatcher(PUBLIC_URLS);

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	private AuthenticationFailureHandler jsonAuthenticationFailureHandler;
	
	@Bean
    public AuthenticationManager authenticationManager(
            final AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//logger.info("filterChainChtulhu");
		
		/*return http
				.csrf().disable()
				.authorizeHttpRequests(customizer -> customizer
						.requestMatchers("/login").permitAll()
						.requestMatchers("/greeting").authenticated()
						.anyRequest().denyAll()
						)
				.exceptionHandling(customizer -> customizer
						.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.FORBIDDEN)))
				.build();*/
		return http.cors().and()
                .csrf().disable()
                .authorizeHttpRequests()
                        .requestMatchers("/", "/login").permitAll()
                        .requestMatchers("/patient/*", "/recipe/*", "/meds/*").authenticated()
                        .anyRequest().hasRole(JwtUserDetailsService.USER).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                /*.exceptionHandling()
                .authenticationEntryPoint((request, response, e) -> 
                {
                  response.setContentType("application/json;charset=UTF-8");
                  response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                  response.getWriter().write("{\"error\":\"666\"}");
                }).and()*/
                .build();
	}
}
