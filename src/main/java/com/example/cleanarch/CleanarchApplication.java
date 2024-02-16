package com.example.cleanarch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class CleanarchApplication {

	public static void main(String[] args) {
		SpringApplication.run(CleanarchApplication.class, args);
	}

}
