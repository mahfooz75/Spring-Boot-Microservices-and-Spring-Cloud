package com.selftech.microservices.userservice.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selftech.microservices.userservice.model.CreateUserRequestModel;
import com.selftech.microservices.userservice.model.CreateUserResponseModel;
import com.selftech.microservices.userservice.model.UserResponseModel;
import com.selftech.microservices.userservice.service.UserService;
import com.selftech.microservices.userservice.shared.UserDto;
import com.selftech.microservices.userservice.shared.Util;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private Environment env;

	@Autowired
	private UserService userService;

	@GetMapping("/status/check")
	public String status() {
		return "User Working on port " + env.getProperty("local.server.port");
	}

	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails) {
		ModelMapper modelMapper = Util.getModelMapper();
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		UserDto createUser = userService.createUser(userDto);
		CreateUserResponseModel createUserResponseModel = modelMapper.map(createUser, CreateUserResponseModel.class);
		return new ResponseEntity<>(createUserResponseModel, HttpStatus.CREATED);
	}

	@GetMapping(value = "/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<UserResponseModel> getUser(@PathVariable String userId) {
		
		UserDto userDto=userService.getUserByUserId(userId);
		ModelMapper modelMapper = Util.getModelMapper();
		UserResponseModel userResponseModel = modelMapper.map(userDto, UserResponseModel.class);
		return ResponseEntity.status(HttpStatus.OK).body(userResponseModel);
	}
	
	@GetMapping(value = "/feign/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<UserResponseModel> getUserFiegnClient(@PathVariable String userId) {
		
		UserDto userDto=userService.getUserByUserIdUsingFeign(userId);
		ModelMapper modelMapper = Util.getModelMapper();
		UserResponseModel userResponseModel = modelMapper.map(userDto, UserResponseModel.class);
		return ResponseEntity.status(HttpStatus.OK).body(userResponseModel);
	}

}
