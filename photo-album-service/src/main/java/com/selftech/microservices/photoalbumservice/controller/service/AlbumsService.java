package com.selftech.microservices.photoalbumservice.controller.service;

import java.util.List;

import com.selftech.microservices.photoalbumservice.controller.entity.AlbumEntity;

public interface AlbumsService {

	List<AlbumEntity> getAlbums(String userId);

}
