package com.my.weatherapp.api;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

  private Long id;

  @NotEmpty
  @Pattern(regexp = "^[A-Za-z0-9-]{1,20}$")
  private String profileName;

  @NotNull
  @Size(min = 1)
  private Set<String> cities;
}
