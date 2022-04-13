package com.selftech.microservices.photoalbumservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PhotoAlbumServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAlbumServiceApplication.class, args);
	}

}
