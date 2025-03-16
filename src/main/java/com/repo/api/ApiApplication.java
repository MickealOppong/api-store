package com.repo.api;

import com.repo.api.impl.PhotoUtilImpl;
import com.repo.api.util.RsaKeyProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
@AllArgsConstructor
public class ApiApplication {
private final PhotoUtilImpl photoUtilImpl;
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(){
		return args -> {
			photoUtilImpl.init();
		};
	}

}
