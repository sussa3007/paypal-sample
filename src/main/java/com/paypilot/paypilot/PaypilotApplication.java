package com.paypilot.paypilot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:/env.yml")
public class PaypilotApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaypilotApplication.class, args);
	}

}
