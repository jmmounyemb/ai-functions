package com.geo.aiFunctions;

import com.geo.aiFunctions.model.WeatherConfigProps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(WeatherConfigProps.class)
@SpringBootApplication
public class AiFunctionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiFunctionsApplication.class, args);
	}

}
