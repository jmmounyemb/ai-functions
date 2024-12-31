package com.geo.aiFunctions.service.impl;

import com.geo.aiFunctions.model.Request;
import com.geo.aiFunctions.model.Response;
import com.geo.aiFunctions.model.WeatherConfigProps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.function.Function;

@Service("currentWeatherService")
@Description("Get the current weather conditions for the given city.")
public class CurrentWeatherServiceImpl implements Function<Request, Response> {
    private static final Logger log = LoggerFactory.getLogger(CurrentWeatherServiceImpl.class);
    private final RestClient restClient;
    private final WeatherConfigProps weatherProps;

    public CurrentWeatherServiceImpl(WeatherConfigProps props) {
        this.weatherProps = props;
        log.debug("Weather API URL: {}", weatherProps.apiUrl());
        this.restClient = RestClient.create(weatherProps.apiUrl());
    }

    @Override
    public Response apply(Request weatherRequest) {
        log.info("Weather Request: {}",weatherRequest);
        Response response = restClient.get()
                .uri("/current.json?key={key}&q={q}", weatherProps.apiKey(), weatherRequest.city())
                .retrieve()
                .body(Response.class);
        log.info("Weather API Response: {}", response);
        return response;
    }
}
