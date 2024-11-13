package com.my.weatherapp.processor;

import com.my.weatherapp.client.OpenWeatherClient;
import com.my.weatherapp.client.model.WeatherPayload;
import com.my.weatherapp.exception.ServiceException;
import com.my.weatherapp.repository.CityRepository;
import com.my.weatherapp.repository.CityWeatherRepository;
import com.my.weatherapp.repository.entity.City;
import com.my.weatherapp.repository.entity.CityWeather;
import java.util.List;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class WeatherDataProcessor {

  private final OpenWeatherClient openWeatherClient;
  private final CityWeatherRepository cityWeatherRepository;
  private final CityRepository cityRepository;

  private static final int PAGE_SIZE = 10;

  @Builder
  public WeatherDataProcessor(OpenWeatherClient openWeatherClient, CityWeatherRepository cityWeatherRepository,
                              CityRepository cityRepository) {
    this.openWeatherClient = openWeatherClient;
    this.cityWeatherRepository = cityWeatherRepository;
    this.cityRepository = cityRepository;
  }

  @Transactional
  public void process() {
    try {
      processPage(PageRequest.of(0, PAGE_SIZE));
    } catch (DataIntegrityViolationException ex) {
      log.error("message=\"encountered error while saving weather data\"", ex);
      throw new ServiceException("error while saving weather data", ex);
    } catch (RuntimeException ex) {
      log.error("message=\"encountered error while saving weather data\"", ex);
      throw ex;
    }
  }

  private void processPage(Pageable pageable) {
    List<City> cities = fetchCities(pageable);  // Fetch cities for the current page
    if (!cities.isEmpty()) {
      fetchWeatherAndSave(cities);  // Process the cities to fetch weather and save it
      checkForNextPage(pageable);   // Check and process the next page of cities if available
    }
  }

  private void checkForNextPage(Pageable pageable) {
    Pageable nextPage = pageable.next();
    List<City> cities = fetchCities(nextPage);  // Fetch cities for the next page
    if (!cities.isEmpty()) {
      processPage(nextPage);  // Recursively process the next page if cities exist
    }
  }

  private List<City> fetchCities(Pageable pageable) {
    log.info("message=\"fetching cities from cities table\"");
    // Fetch the cities from the repository (blocking call)
    return cityRepository.findAll(pageable).getContent();
  }

  private void fetchWeatherAndSave(List<City> cities) {
    final List<City> cityWeatherList = cities.stream()
        .map(this::mapWeather)
        .toList();
    saveToDb(cityWeatherList);  // Save the CityWeather entity to the database
  }

  private City mapWeather(City city) {
    log.info("message=\"fetching latest weather for {} city\"", city);
    WeatherPayload weather = openWeatherClient.pullCityWeatherData(city);
    return this.mapToEntity(city, weather);
  }

  private void saveToDb(List<City> cityWeatherList) {
    log.debug("message=\"saving city weather for city: {}\"", cityWeatherList);
     cityRepository.saveAllAndFlush(cityWeatherList);  // Ensure the City is saved and merged in the current session
  }

  private City mapToEntity(City city, WeatherPayload payload) {

    final CityWeather cityWeather = CityWeather.builder()
        .cityId(city.getId())
        .main(payload.weather().get(0).main())
        .temperature(payload.main().temp())
        .feelsLike(payload.main().feelsLike())
        .temperatureMin(payload.main().tempMin())
        .temperatureMax(payload.main().tempMax())
        .windSpeed(payload.wind().speed())
        .pressure(payload.main().pressure())
        .humidity(payload.main().humidity())
        .groundLevel(payload.main().grndLevel())
        .seaLevel(payload.main().seaLevel())
        .build();
    city.setCityWeather(cityWeather);
    return city;
  }
}
