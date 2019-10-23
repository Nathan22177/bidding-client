package com.nathan22177.biddingclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.nathan22177"})
@EnableJpaRepositories("com.nathan22177.biddingclient.repository")
public class BiddingClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiddingClientApplication.class, args);
	}

}
