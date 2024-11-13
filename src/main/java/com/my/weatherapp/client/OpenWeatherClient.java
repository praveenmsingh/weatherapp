package com.my.weatherapp.client;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.my.weatherapp.client.model.WeatherPayload;
import com.my.weatherapp.repository.entity.City;
import com.my.weatherapp.validator.BeanValidationHelper;
import jakarta.validation.ConstraintViolationException;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class OpenWeatherClient {

  private final String openweatherHost;
  private final String openweatherPath;
  private final String authKey;
  private final WebClient webClient;
  private final ErrorHandler errorHandler;
  private final BeanValidationHelper validationHelper;

  public OpenWeatherClient(@Value("${services.openWeather.endpoint}") String openweatherHost,
                           @Value("${services.openWeather.weatherDataPath}") String openweatherPath,
                           @Value("${services.openWeather.authKey}") String authKey,
                           WebClient webClient, ErrorHandler errorHandler, BeanValidationHelper validationHelper) {
    this.openweatherHost = openweatherHost;
    this.openweatherPath = openweatherPath;
    this.authKey = authKey;
    this.webClient = webClient;
    this.errorHandler = errorHandler;
    this.validationHelper = validationHelper;
  }

  public WeatherPayload pullCityWeatherData(City cityDto) {
    return webClient.get()
        .uri(openweatherHost + openweatherPath, cityDto.getLatitude(), cityDto.getLongitude(), authKey)
        .accept(APPLICATION_JSON)
        .exchangeToMono(resp -> {
          final HttpStatusCode status = resp.statusCode();
          if (status.isError()) {
            return Mono.error(errorHandler.mapDownstreamError(status, "weather-data"));
          }
          return resp.bodyToMono(WeatherPayload.class)
              .map(validationHelper::applyValidations);
        })
        .onErrorMap(ConstraintViolationException.class, errorHandler::mapResponseValidationErrors)
        .onErrorMap(WebClientException.class, ex -> {
          log.error("message=\"timeout while calling openweather current-weather API, error=\"", ex);
          return errorHandler.mapIOErrors(ex);
        })
        .block(Duration.ofSeconds(10L));
  }
}
