package com.geo.aiFunctions.controller;

import com.geo.aiFunctions.service.IWeatherCityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final IWeatherCityService weatherCityService;

    public WeatherController(IWeatherCityService weatherCityService) {
        this.weatherCityService = weatherCityService;
    }

    @GetMapping("/current")
    public ResponseEntity<String> getCityCurrentWeather(@RequestParam String question) {
        return ResponseEntity.ok(weatherCityService.getCityCurrentWeather(question));
    }
}
