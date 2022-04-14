package com.selftech.microservices.userservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.selftech.microservices.userservice.entity.UserEntity;
import com.selftech.microservices.userservice.feignclient.AlbumServiceClient;
import com.selftech.microservices.userservice.model.AlbumResponseModel;
import com.selftech.microservices.userservice.repository.UserRepository;
import com.selftech.microservices.userservice.shared.UserDto;
import com.selftech.microservices.userservice.shared.Util;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	UserRepository userRepository;
	BCryptPasswordEncoder bCryptPasswordEncoder;
	RestTemplate restTemplate;
	AlbumServiceClient albumServiceClient;
	Environment env;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
			RestTemplate restTemplate, AlbumServiceClient albumServiceClient, Environment env) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.restTemplate = restTemplate;
		this.albumServiceClient = albumServiceClient;
		this.env = env;
	}

	@Override
	public UserDto createUser(UserDto userDetails) {

		userDetails.setUserId(UUID.randomUUID().toString());
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		ModelMapper mapper = Util.getModelMapper();
		UserEntity userEntity = mapper.map(userDetails, UserEntity.class);
		UserEntity savedEntity = userRepository.save(userEntity);

		return mapper.map(savedEntity, UserDto.class);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(username);
		if (userEntity == null) {
			throw new UsernameNotFoundException(username);
		}
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true,
				new ArrayList<>());
	}

	@Override
	public UserDto getUserDetailsByEmail(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null) {
			throw new UsernameNotFoundException(email);
		}
		ModelMapper mapper = Util.getModelMapper();
		return mapper.map(userEntity, UserDto.class);
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserDto userDto = getUserEntity(userId);
		String albumUrl = String.format(env.getProperty("albums.url"), userId);
		ResponseEntity<List<AlbumResponseModel>> albumListResponse = restTemplate.exchange(albumUrl, HttpMethod.GET,
				null, new ParameterizedTypeReference<List<AlbumResponseModel>>() {
				});
		userDto.setAlbums(albumListResponse.getBody());
		return userDto;
	}

	@Override
	public UserDto getUserByUserIdUsingFeign(String userId) {
		UserDto userDto = getUserEntity(userId);
		List<AlbumResponseModel> albums = null;
		try {
			albums = albumServiceClient.getAlbums(userId);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
		userDto.setAlbums(albums);
		return userDto;
	}

	private UserDto getUserEntity(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		if (userEntity == null) {
			throw new UsernameNotFoundException(userId);
		}
		ModelMapper mapper = Util.getModelMapper();
		return mapper.map(userEntity, UserDto.class);
	}

}
