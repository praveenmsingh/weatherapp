package com.my.weatherapp.repository.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cities", schema = "myweatherdb")
public class City {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 50)
  private String name;

  @Column(nullable = false)
  private String latitude;

  @Column(nullable = false)
  private String longitude;

  @JsonManagedReference
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "city_id", referencedColumnName = "city_id")
  private CityWeather cityWeather;

  @ManyToMany(mappedBy = "cities")
  private Set<WeatherProfile> weatherProfiles = new HashSet<>();
}
