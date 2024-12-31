package com.geo.aiFunctions.service.impl;

import com.geo.aiFunctions.service.IWeatherCityService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherCityServiceImpl implements IWeatherCityService {

    private final ChatClient chatClient;

    public WeatherCityServiceImpl(ChatClient.Builder chatClient) {
        this.chatClient = chatClient
                .defaultFunctions("currentWeatherService")
                .build();
    }

    @Override
    public String getCityCurrentWeather(String question) {
        SystemMessage systemMessage = new SystemMessage("You are a helpful and friendly AI assistant who can answer questions about city's weather around the world.");
        UserMessage userMessage = new UserMessage(question);

        return chatClient.prompt(new Prompt(List.of(systemMessage, userMessage)))
                .call()
                .content();
    }
}
