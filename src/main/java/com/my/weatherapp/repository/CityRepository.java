package com.my.weatherapp.repository;

import com.my.weatherapp.repository.entity.City;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.swing.text.html.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

  Optional<City> findByName(String name);

  Set<City> findAllByNameIn(Set<String> name);
}
