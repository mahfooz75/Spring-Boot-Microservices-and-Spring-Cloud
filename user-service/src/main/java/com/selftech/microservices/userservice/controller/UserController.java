package com.selftech.microservices.userservice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selftech.microservices.userservice.model.CreateUserRequestModel;
import com.selftech.microservices.userservice.service.UserService;
import com.selftech.microservices.userservice.shared.UserDto;
import com.selftech.microservices.userservice.shared.Util;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	Environment env;
	
	@Autowired
	private UserService userService;

	@GetMapping("/status/check")
	public String status() {
		return "User Working on port " + env.getProperty("local.server.port");
	}

	@PostMapping
	public String createUser(@Valid @RequestBody CreateUserRequestModel userDetails) {
		UserDto userDto = Util.getModelMapper().map(userDetails, UserDto.class);
		userService.createUser(userDto);
		return "Create User method called...";
	}

}
