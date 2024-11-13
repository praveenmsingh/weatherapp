package com.my.weatherapp.service;

import com.my.weatherapp.dto.UserDto;
import com.my.weatherapp.dto.UserResponseDto;

public interface UserService {

  UserResponseDto createUserRegistration(UserDto user);

}
