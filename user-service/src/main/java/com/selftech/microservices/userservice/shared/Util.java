package com.selftech.microservices.userservice.shared;

import java.io.IOException;
import java.io.InputStream;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Util {

	public ModelMapper getModelMapper() {
		ModelMapper mapper=new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return mapper;
	}
	
	public <T> T convertJsonToObject(InputStream inStream, Class<T> t) throws IOException {
		ObjectMapper objMapper=new ObjectMapper();
		return objMapper.readValue(inStream, t);
	}
	
}