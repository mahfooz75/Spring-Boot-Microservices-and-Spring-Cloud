package com.selftech.microservices.userservice.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selftech.microservices.userservice.model.CreateUserRequestModel;
import com.selftech.microservices.userservice.model.CreateUserResponseModel;
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
	public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails) {
		ModelMapper modelMapper = Util.getModelMapper();
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		UserDto createUser = userService.createUser(userDto);
		CreateUserResponseModel createUserResponseModel = modelMapper.map(createUser, CreateUserResponseModel.class);
		return new ResponseEntity<>(createUserResponseModel, HttpStatus.CREATED);
	}

}
