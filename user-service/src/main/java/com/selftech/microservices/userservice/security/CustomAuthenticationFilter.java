package com.selftech.microservices.userservice.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.selftech.microservices.userservice.model.LoginRequestModel;
import com.selftech.microservices.userservice.service.UserService;
import com.selftech.microservices.userservice.shared.UserDto;
import com.selftech.microservices.userservice.shared.UserServiceConstant;
import com.selftech.microservices.userservice.shared.Util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private UserService userService;
	private Environment env;

	public CustomAuthenticationFilter(UserService userService, Environment env,
			AuthenticationManager authenticationManager) {
		this.userService = userService;
		this.env = env;
		super.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			LoginRequestModel loginRequestModel = Util.convertJsonToObject(request.getInputStream(),
					LoginRequestModel.class);
			return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
					loginRequestModel.getEmail(), loginRequestModel.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			throw new SecurityException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		User user = (User) auth.getPrincipal();
		String username = user.getUsername();
		UserDto userDetails = userService.getUserDetailsByEmail(username);
		String token = generateJwtToken(userDetails, env);
		response.addHeader("token", token);
		response.addHeader("userId", userDetails.getUserId());
	}

	private String generateJwtToken(UserDto userDetails, Environment env) {
		return Jwts.builder().setSubject(userDetails.getUserId())
				.setExpiration(new Date(System.currentTimeMillis()
						+ Long.parseLong(env.getProperty(UserServiceConstant.TOKEN_EXPIRATION_TIME))))
				.signWith(SignatureAlgorithm.HS512, env.getProperty(UserServiceConstant.TOKEN_SECRET)).compact();

	}

}
