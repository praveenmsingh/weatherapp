package com.my.weatherapp.repository;

import com.my.weatherapp.repository.entity.WeatherProfile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherProfileRepository extends JpaRepository<WeatherProfile, Long> {

  Optional<WeatherProfile> findByIdAndUserId(Long id, Long userId);

  Optional<WeatherProfile> findByNameAndUserId(String name, Long userId);
}
