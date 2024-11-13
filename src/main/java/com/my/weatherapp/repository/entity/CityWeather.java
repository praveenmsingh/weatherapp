package com.my.weatherapp.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "city_weather", schema = "myweatherdb")
public class CityWeather {

  @Id
  @Column(name = "city_id")
  private Long cityId;

  @Column(nullable = false)
  private String main;

  @Column(name = "wind_speed", nullable = false)
  private String windSpeed;

  @Column(nullable = false)
  private String temperature;

  @Column(name = "feels_like", nullable = false)
  private String feelsLike;

  @Column(name = "temperature_min", nullable = false)
  private String temperatureMin;

  @Column(name = "temperature_max", nullable = false)
  private String temperatureMax;

  @Column(nullable = false)
  private String pressure;

  @Column(nullable = false)
  private String humidity;

  @Column(name = "ground_level", nullable = false)
  private String groundLevel;

  @Column(name = "sea_level", nullable = false)
  private String seaLevel;

  @Column(name = "created_at", updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at")
  private Instant updatedAt;

  @PrePersist
  public void prePersist() {
    Instant now = Instant.now();
    this.createdAt = now;
    this.updatedAt = now;
  }

  @PreUpdate
  public void preUpdate() {
    this.updatedAt = Instant.now();
  }
}
