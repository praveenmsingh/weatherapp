package com.my.weatherapp.mapper;

import static org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import com.my.weatherapp.client.model.WeatherPayload;
import com.my.weatherapp.repository.entity.City;
import com.my.weatherapp.repository.entity.CityWeather;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE,
    nullValueIterableMappingStrategy = RETURN_DEFAULT, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CityWeatherMapper {

  @Mapping(target = "cityId", source = "city.id")
  @Mapping(target = "main", expression = "java(mapMainForecast(payload))")
  @Mapping(target = "temperature", source = "payload.main.temp")
  @Mapping(target = "feelsLike", source = "payload.main.feelsLike")
  @Mapping(target = "temperatureMin", source = "payload.main.tempMin")
  @Mapping(target = "temperatureMax", source = "payload.main.tempMax")
  @Mapping(target = "windSpeed", source = "payload.wind.speed")
  @Mapping(target = "pressure", source = "payload.main.pressure")
  @Mapping(target = "humidity", source = "payload.main.humidity")
  @Mapping(target = "groundLevel", source = "payload.main.grndLevel")
  @Mapping(target = "seaLevel", source = "payload.main.seaLevel")
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  CityWeather toEntity(City city, WeatherPayload payload);

  default String mapMainForecast(WeatherPayload payload) {
    return payload.weather().get(0).main();
  }
}
