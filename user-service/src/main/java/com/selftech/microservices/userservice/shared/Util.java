package com.selftech.microservices.userservice.shared;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Util {

	public ModelMapper getModelMapper() {
		ModelMapper mapper=new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return mapper;
	}
	
}