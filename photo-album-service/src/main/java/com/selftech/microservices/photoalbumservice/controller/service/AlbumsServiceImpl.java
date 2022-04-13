package com.selftech.microservices.photoalbumservice.controller.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.selftech.microservices.photoalbumservice.controller.entity.AlbumEntity;

@Service
public class AlbumsServiceImpl implements AlbumsService {

	@Override
	public List<AlbumEntity> getAlbums(String userId) {
		List<AlbumEntity> returnValues = new ArrayList<>();

		AlbumEntity albumEntity1 = new AlbumEntity();
		albumEntity1.setUserId(userId);
		albumEntity1.setAlbumID("albumId1");
		albumEntity1.setDescription("album 1 description");
		albumEntity1.setId(1L);
		albumEntity1.setName("album 1 name");

		AlbumEntity albumEntity2 = new AlbumEntity();
		albumEntity2.setUserId(userId);
		albumEntity2.setAlbumID("albumId2");
		albumEntity2.setDescription("album 2 description");
		albumEntity2.setId(2L);
		albumEntity2.setName("album 2 name");

		returnValues.add(albumEntity1);
		returnValues.add(albumEntity2);
		
		return returnValues;
	}

}
