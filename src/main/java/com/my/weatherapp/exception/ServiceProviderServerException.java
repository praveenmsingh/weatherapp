package com.my.weatherapp.exception;

import com.my.weatherapp.api.ApiError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ServiceProviderServerException extends RuntimeException {

  @Getter
  private ApiError apiError;

  private static final long serialVersionUID = 44752435756423243L;

  public ServiceProviderServerException(String message, ApiError apiError) {
    super(message);
    this.apiError = apiError;
  }

  public ServiceProviderServerException(String message, Throwable cause) {
    super(message, cause);
  }

  public ServiceProviderServerException(String message) {
    super(message);
  }
}
