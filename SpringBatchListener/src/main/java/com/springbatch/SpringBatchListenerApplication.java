package com.springbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class SpringBatchListenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchListenerApplication.class, args);
	}
}
