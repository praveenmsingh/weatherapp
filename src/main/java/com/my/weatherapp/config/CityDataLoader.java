package com.my.weatherapp.config;

import com.my.weatherapp.api.CityCoOrdinates;
import com.my.weatherapp.exception.ServiceException;
import com.my.weatherapp.repository.CityRepository;
import com.my.weatherapp.repository.entity.City;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile({"!test"})
public class CityDataLoader implements CommandLineRunner {

  private final CityRepository cityRepository;
  private final List<CityCoOrdinates> cityData;

  public CityDataLoader(CityRepository cityRepository, List<CityCoOrdinates> availableCities) {
    this.cityRepository = cityRepository;
    this.cityData = availableCities;
  }

  @Override
  public void run(String... args) {
    try {
      log.info("message=\"attempting to load city data into database. cities: {}\"", cityData.size());
      cityData.stream()
        .map(city -> City.builder()
            .name(city.getName())
            .latitude(city.getLatitude())
            .longitude(city.getLongitude())
            .build())
        .filter(city -> cityRepository.findByName(city.getName()).isEmpty())
        .forEach(cityRepository::save);
      log.info("message=\"loaded city data into database successfully\"");
    } catch (Exception ex) {
      log.error("message=\"error during application startup. Unable to load city data successfully. ex= \"", ex);
      throw new ServiceException("unable to load city data successfully", ex);
    }
  }

}
