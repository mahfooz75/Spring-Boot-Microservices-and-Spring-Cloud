package com.selftech.microservices.userservice.service;

import com.selftech.microservices.userservice.shared.UserDto;

public interface UserService {
	UserDto createUser(UserDto userDetails);
}
