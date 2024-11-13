package com.my.weatherapp.repository;

import com.my.weatherapp.repository.entity.CityWeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityWeatherRepository extends JpaRepository<CityWeather, Long> {
}
