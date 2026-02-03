package com.geopokrovskiy.okved_game_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OkvedGameTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(OkvedGameTestApplication.class, args);
	}

}
