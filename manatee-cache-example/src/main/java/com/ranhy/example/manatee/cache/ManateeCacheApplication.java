package com.ranhy.example.manatee.cache;

import com.ranhy.framework.manatee.cache.client.annotation.EnableManateeCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableManateeCache
public class ManateeCacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManateeCacheApplication.class, args);
	}

}
