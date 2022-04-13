package com.selftech.microservices.photoalbumservice.controller.entity;

import lombok.Data;

@Data
public class AlbumEntity {
	private String userId;
	private String albumID;
	private String description;
	private Long id;
	private String name;
}
