package com.my.weatherapp.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherPayload(@NotEmpty String name,
                             @NotNull @Valid Main main,
                             @NotNull @Size(min = 1) List<@Valid Weather> weather,
                             @NotNull @Valid Wind wind) {

}
