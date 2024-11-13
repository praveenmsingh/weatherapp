package com.my.weatherapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ServiceProviderClientException extends RuntimeException {

  @Getter
  private HttpStatus httpStatus;

  private static final long serialVersionUID = 4475248756423243L;

  public ServiceProviderClientException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }

  public ServiceProviderClientException(String message, Throwable cause) {
    super(message, cause);
  }

  public ServiceProviderClientException(String message) {
    super(message);
  }
}
