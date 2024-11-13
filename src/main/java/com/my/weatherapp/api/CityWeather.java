package com.my.weatherapp.api;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class CityWeather {

  private final long id;

  private final String name;

  private final String latitude;

  private final String longitude;

  private final String main;

  private final String windSpeed;

  private final String temperature;

  private final String feelsLike;

  private final String temperatureMin;

  private final String temperatureMax;

  private final String pressure;

  private final String humidity;

  private final String groundLevel;

  private final String seaLevel;

  private final Instant createdAt;

  private final Instant updatedAt;
}
