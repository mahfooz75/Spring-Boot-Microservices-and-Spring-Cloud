package com.selftech.microservices.photoalbumservice.controller;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selftech.microservices.photoalbumservice.controller.entity.AlbumEntity;
import com.selftech.microservices.photoalbumservice.controller.model.AlbumResponseModel;
import com.selftech.microservices.photoalbumservice.controller.service.AlbumsService;

@RestController
@RequestMapping("/users/{id}/albums")
public class AlbumsController {

	@Autowired
	private AlbumsService albumsService;

	@GetMapping
	public List<AlbumResponseModel> userAlbums(@PathVariable String id) {
		List<AlbumResponseModel> returnValue = Collections.emptyList();
		List<AlbumEntity> albumsEntities = albumsService.getAlbums(id);
		if (albumsEntities == null || albumsEntities.isEmpty()) {
			return returnValue;
		}

		Type listType = new TypeToken<List<AlbumResponseModel>>() {
		}.getType();
		returnValue = new ModelMapper().map(albumsEntities, listType);
		return returnValue;
	}

}
