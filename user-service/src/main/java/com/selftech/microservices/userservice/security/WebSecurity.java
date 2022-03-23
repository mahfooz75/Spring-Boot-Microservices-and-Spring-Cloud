package com.selftech.microservices.userservice.security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.selftech.microservices.userservice.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private UserService userService;
	private Environment env;

	@Autowired
	public WebSecurity(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService, Environment env) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userService = userService;
		this.env = env;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/**").hasIpAddress("127.0.0.1").and()
				.addFilter(getAuthenticationFilter());
	}

	private Filter getAuthenticationFilter() throws Exception {
		CustomAuthenticationFilter authFilter = new CustomAuthenticationFilter(userService, env, authenticationManager());
		authFilter.setAuthenticationManager(authenticationManager());
		authFilter.setFilterProcessesUrl(env.getProperty("login.url.path"));
		return authFilter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}

}
