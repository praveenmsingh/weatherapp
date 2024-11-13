package com.my.weatherapp.api;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public final class CityCoOrdinates {
  private final String name;
  private final String latitude;
  private final String longitude;

}
