package com.selftech.microservices.cloudgateway;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class GlobalFiltersConfiguration {

	@Bean
	@Order(1)
	public GlobalFilter secondPreFilter() {
		return (exchange,chain)->{
			log.info("My second global pre-filter is executed...");
			return chain.filter(exchange).then(Mono.fromRunnable(()->{
				log.info("My second global post-filter is executed...");
			}));
		};
	}
	
	@Bean
	@Order(2)
	public GlobalFilter thirdPreFilter() {
		return (exchange,chain)->{
			log.info("My third global pre-filter is executed...");
			return chain.filter(exchange).then(Mono.fromRunnable(()->{
				log.info("My third global post-filter is executed...");
			}));
		};
	}
	
	@Bean
	@Order(3)
	public GlobalFilter fourthPreFilter() {
		return (exchange,chain)->{
			log.info("My fourth global pre-filter is executed...");
			return chain.filter(exchange).then(Mono.fromRunnable(()->{
				log.info("My fourth global post-filter is executed...");
			}));
		};
	}
	
}
