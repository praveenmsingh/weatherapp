package com.my.weatherapp.config;

import com.my.weatherapp.processor.WeatherDataProcessor;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile({"!test"})
@RequiredArgsConstructor
public class WeatherDataLoader {

  private final WeatherDataProcessor weatherDataProcessor;

  @Transactional
  @Scheduled(initialDelay = 5000L, fixedRate = 900000L)
  public void loadWeatherData() {
    weatherDataProcessor.process();
  }
}
