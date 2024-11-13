package com.my.weatherapp.dto;

import com.my.weatherapp.api.CityWeather;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public final class WeatherProfileResponseDto {
  private final Long id;
  private final Long userId;
  private final String name;

  private final List<CityWeather> cities;

}
