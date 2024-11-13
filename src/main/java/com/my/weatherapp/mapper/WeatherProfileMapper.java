package com.my.weatherapp.mapper;

import static com.my.weatherapp.api.CityWeather.*;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import com.my.weatherapp.api.CityWeather;
import com.my.weatherapp.dto.WeatherProfileResponseDto;
import com.my.weatherapp.repository.entity.City;
import com.my.weatherapp.repository.entity.WeatherProfile;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface WeatherProfileMapper {

  @Mapping(target = "userId", source = "user.id")
  @Mapping(target = "cities", ignore = true)
  WeatherProfileResponseDto toResponseDto(WeatherProfile weatherProfile);

  @Mapping(target = "userId", source = "weatherProfile.user.id")
  @Mapping(target = "id", source = "weatherProfile.id")
  @Mapping(target = "cities", expression = "java(mapCityWeather(weatherProfile, cities))")
  WeatherProfileResponseDto toResponseDataWithWeather(WeatherProfile weatherProfile, List<City> cities);

  default List<CityWeather> mapCityWeather(WeatherProfile weatherProfile, List<City> cities) {
    return cities.stream()
        .map(city -> CityWeather.builder()
            .id(city.getId())
            .name(city.getName())
            .latitude(city.getLatitude())
            .longitude(city.getLongitude())
            .main(city.getCityWeather().getMain())
            .temperature(city.getCityWeather().getTemperature())
            .temperatureMin(city.getCityWeather().getTemperatureMin())
            .temperatureMax(city.getCityWeather().getTemperatureMax())
            .feelsLike(city.getCityWeather().getFeelsLike())
            .pressure(city.getCityWeather().getPressure())
            .humidity(city.getCityWeather().getHumidity())
            .groundLevel(city.getCityWeather().getGroundLevel())
            .seaLevel(city.getCityWeather().getSeaLevel())
            .updatedAt(city.getCityWeather().getUpdatedAt())
            .build())
        .toList();
  }
}
