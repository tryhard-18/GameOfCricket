package com.tekion.gameofcricket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
public class GameOfCricketApplication {
	public static void main(String[] args) {
		SpringApplication.run(GameOfCricketApplication.class, args);
	}
}
