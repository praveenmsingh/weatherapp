package com.my.weatherapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public final class UserDto {
  private final String name;
  private final String email;

}
