package com.selftech.microservices.userservice.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.selftech.microservices.userservice.model.AlbumResponseModel;

@FeignClient(name = "albums-ws")
public interface AlbumServiceClient {

	@GetMapping("/users/{id}/albumss")
	public List<AlbumResponseModel> getAlbums(@PathVariable String id);

}
