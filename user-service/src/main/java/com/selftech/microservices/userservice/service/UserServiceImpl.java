package com.selftech.microservices.userservice.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.selftech.microservices.userservice.entity.UserEntity;
import com.selftech.microservices.userservice.repository.UserRepository;
import com.selftech.microservices.userservice.shared.UserDto;
import com.selftech.microservices.userservice.shared.Util;

@Service
public class UserServiceImpl implements UserService {

	UserRepository userRepository;
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}


	@Override
	public UserDto createUser(UserDto userDetails) {
		
		userDetails.setUserId(UUID.randomUUID().toString());
		ModelMapper mapper=Util.getModelMapper();
		UserEntity userEntity = mapper.map(userDetails, UserEntity.class);
		userEntity.setEncryptedPassword("test");
		UserEntity savedEntity = userRepository.save(userEntity);
		
		UserDto userDto = mapper.map(savedEntity, UserDto.class);
		return userDto;
	}

}
