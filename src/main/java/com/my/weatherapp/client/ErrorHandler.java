package com.my.weatherapp.client;

import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

import com.my.weatherapp.exception.ServiceProviderClientException;
import com.my.weatherapp.exception.ServiceProviderServerException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientException;

@Slf4j
@Component
public class ErrorHandler {

  private static final String ERROR_LOG = "error while fetching latest weather data for city %";

  public Exception mapDownstreamError(final HttpStatusCode statusCode, final String clientName) {
    log.error("message=\"{}\"", ERROR_LOG);
    if (statusCode.is4xxClientError() && statusCode != TOO_MANY_REQUESTS) {
      throw new ServiceProviderClientException(ERROR_LOG, HttpStatus.valueOf(statusCode.value()));
    }
    throw new ServiceProviderServerException(ERROR_LOG);
  }

  public ServiceProviderServerException mapResponseValidationErrors(ConstraintViolationException ex) {
    throw new ServiceProviderServerException("response validation error while fetching latest weather data for city %");
  }

  public ServiceProviderServerException mapIOErrors(WebClientException ex) {
    throw new ServiceProviderServerException("error while calling weather-data API", ex);
  }
}
