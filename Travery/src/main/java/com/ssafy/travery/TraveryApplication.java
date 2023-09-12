package com.ssafy.travery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ssafy.travery"})
public class TraveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(TraveryApplication.class, args);
		// test
	}

}
