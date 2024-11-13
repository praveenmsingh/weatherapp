package com.my.weatherapp.config;

import com.my.weatherapp.api.CityCoOrdinates;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "services")
public class CityDataConfig {

  private List<CityCoOrdinates> availableCities;

  @Bean("availableCities")
  public List<CityCoOrdinates> availableCities() {
    log.info("loading cities-------");
    return this.availableCities;
  }
}
