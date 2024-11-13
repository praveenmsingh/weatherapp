package com.my.weatherapp.service;

import com.my.weatherapp.dto.UserProfileDto;
import com.my.weatherapp.dto.WeatherProfileResponseDto;
import java.util.List;

public interface WeatherProfileService {

  WeatherProfileResponseDto createUserProfileRegistration(UserProfileDto userProfileDto);

  WeatherProfileResponseDto modifyUserProfile(UserProfileDto userProfileDto);

  void deleteUserProfile(Long userId, Long profileId);

  WeatherProfileResponseDto getUserProfile(Long userId, Long profileId);

  List<WeatherProfileResponseDto> getAllUserProfilesForUserId(Long userId);

}
