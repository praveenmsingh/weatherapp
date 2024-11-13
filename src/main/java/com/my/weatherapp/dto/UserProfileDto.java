package com.my.weatherapp.dto;

import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@Getter
public final class UserProfileDto {
  private final Long userId;
  private final Long userProfileId;
  private final String name;
  private final Set<String> cities;

}
