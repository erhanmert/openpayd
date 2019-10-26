package com.openpayd.openpayd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"com.openpayd.openpayd"})
public class OpenpaydApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenpaydApplication.class, args);
	}

}
