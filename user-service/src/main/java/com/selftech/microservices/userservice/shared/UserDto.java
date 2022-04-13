package com.selftech.microservices.userservice.shared;

import java.io.Serializable;
import java.util.List;

import com.selftech.microservices.userservice.model.AlbumResponseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String userId;
	private String encryptedPassword;
	private List<AlbumResponseModel> albums;
}
