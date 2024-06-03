package com.flowform.FlowForm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FlowFormApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowFormApplication.class, args);
	}

}
