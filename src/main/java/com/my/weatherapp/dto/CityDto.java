package com.my.weatherapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public final class CityDto {
  private final long id;
  private final String name;
  private final String latitude;
  private final String longitude;

}
