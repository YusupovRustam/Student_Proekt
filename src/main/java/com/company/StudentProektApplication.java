package com.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class StudentProektApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentProektApplication.class, args);
	}

}
