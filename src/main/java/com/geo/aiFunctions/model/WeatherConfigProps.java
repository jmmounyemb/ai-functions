package com.geo.aiFunctions.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("weather")
public record WeatherConfigProps(String apiKey, String apiUrl) {
}
