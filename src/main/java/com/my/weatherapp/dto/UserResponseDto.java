package com.my.weatherapp.dto;

import java.util.List;

public record UserResponseDto(Long id, String name, String email, List<WeatherProfileResponseDto> profileDtoList) {

}
