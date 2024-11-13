package com.my.weatherapp.client.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Main(@NotEmpty String temp,
                   @JsonAlias(value = "feels_like") @NotEmpty String feelsLike,
                   @JsonAlias(value = "temp_min") @NotEmpty String tempMin,
                   @JsonAlias(value = "temp_max") @NotEmpty String tempMax,
                   @NotEmpty String pressure,
                   @NotEmpty String humidity,
                   @JsonAlias(value = "sea_level") @NotEmpty String seaLevel,
                   @JsonAlias(value = "grnd_level") @NotEmpty String grndLevel) {
}
