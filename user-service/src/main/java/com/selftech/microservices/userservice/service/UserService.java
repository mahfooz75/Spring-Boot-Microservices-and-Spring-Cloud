package com.selftech.microservices.userservice.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.selftech.microservices.userservice.shared.UserDto;

public interface UserService extends UserDetailsService{
	UserDto createUser(UserDto userDetails);
	UserDto getUserDetailsByEmail(String email);
}
