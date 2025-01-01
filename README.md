# Spring AI Functions

Welcome to the Spring AI Functions! This application demonstrates how to connect the LLM capabilities with external tools 
and APIs. One of the drawback of LLM is that they cannot offer accurate information about post-cut-off events. For instance, 
if we were to ask an LLM "What is the  weather currently like in Montreal", well that is a real time information that we 
are asking and the LLM, will not be able to give us an answer. Spring AI Functions provide a way to mitigate that by providing
the AI model with metadata about our own function that it can use to retrieve that information as it processes the prompt. 

## Project Overview

This project showcases the integration of Spring AI Functions with Spring Boot to create a chatbot that answer real-time questions by calling our own functions.

## Project Requirements

- Java 21
- Maven 3.6.3 or newer
- Spring Boot 3.4.1
- Spring AI 1.0.0-M4

## Getting Started

To get started with this project, ensure you have Java 21 and Maven installed on your system. Then, follow these steps:

1. Set up your API keys:
   - Create a file `application.yml` in the `src/main/resources` directory
     - Add the following line, replacing `<ADD Your Weather API key here>` with your actual Weather API key:
       ```
       weather:
        api-key: <ADD Your Weather API key here>
        api-url: http://api.weatherapi.com/v1
       ```
     - Specify Open AI API Key:     
         ```
         spring:
          ai:
           openai.api-key: <ADD Your Open API key here>
           openai.chat.options.model: gpt-4
        ```

2. Build the project:
   ```
   mvn clean install
   ```

3. Run the application:
   ```
   mvn spring-boot:run
   ```

The application will start, and you'll be able to access the chat endpoints.

## How to Run the Application

Once the application is running, you can interact with it using the following endpoints:

   ```
   GET http://localhost:8080/weather/current?question=Your question here
   ```
   Example of question: _What is the  weather currently like in Montreal_

You can use tools like cURL, Postman, or create a simple frontend to interact with these endpoints.

## Relevant Code Examples

### Weather Service

The `WeatherCityServiceImpl` class is where we provide the function that need to be called when the LLM doesn't have the answer. If AI 
Model determines that it needs additional information about the temperature in a given location, it will start a server-side 
generated request/response interaction. The AI Model invokes a client side function. The AI Model provides method invocation 
details as JSON, and it is the responsibility of the client to execute that function and return the response.

```java
@Service
public class WeatherCityServiceImpl implements IWeatherCityService {

   private final ChatClient chatClient;

   public WeatherCityServiceImpl(ChatClient.Builder chatClient) {
      this.chatClient = chatClient
              .defaultFunctions("currentWeatherService") // The function that will be called when the AI Model determines that it
                                                        // needs additional information about the temperature in a given location
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
```

### Application Configuration

The `AiFunctionsApplication` class is a standard Spring Boot application class:

```java
@EnableConfigurationProperties(WeatherConfigProps.class)
@SpringBootApplication
public class AiFunctionsApplication {
    public static void main(String[] args) {
        SpringApplication.run(AiFunctionsApplication.class, args);
    }
}
```

This class bootstraps the Spring application context and runs the embedded web server.

## Conclusion

This Spring AI Chat Bot can literally tell us the current weather of every location we provide. It showcases the flexibility 
of Spring AI which greatly simplifies the code we need to write to support function invocation. It brokers the function 
invocation conversation for us. 

Happy coding!
