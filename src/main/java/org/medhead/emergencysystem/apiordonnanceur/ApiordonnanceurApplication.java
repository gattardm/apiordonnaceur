package org.medhead.emergencysystem.apiordonnanceur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiordonnanceurApplication  {

	@Autowired
	private CustomProperties props;

	public static void main(String[] args) {
		SpringApplication.run(ApiordonnanceurApplication.class, args);
	}

}
