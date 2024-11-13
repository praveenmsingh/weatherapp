package com.my.weatherapp.controllers;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

import com.my.weatherapp.api.ApiError;
import com.my.weatherapp.api.ApiErrorBuilder;
import com.my.weatherapp.exception.InvalidDataException;
import com.my.weatherapp.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import java.util.NoSuchElementException;
import javax.naming.ServiceUnavailableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  private final ApiErrorBuilder apiErrorBuilder;

  @ExceptionHandler({UnsupportedOperationException.class, HttpRequestMethodNotSupportedException.class})
  public ResponseEntity<ApiError> handleUnsupportedOperationException(Exception exception, HttpServletRequest request) {
    final ApiError apiError = apiErrorBuilder.buildErrorResponse(exception, METHOD_NOT_ALLOWED);

    log.error("status: {}, exception: {}, stactrace: {}", METHOD_NOT_ALLOWED, exception, exception.getStackTrace());
    return new ResponseEntity<>(apiError, apiError.getHttpStatus());
  }

  @ExceptionHandler({ServiceUnavailableException.class})
  public ResponseEntity<ApiError> handleServiceUnavailableException(Exception exception, HttpServletRequest request) {
    final ApiError apiError = apiErrorBuilder.buildErrorResponse(exception, SERVICE_UNAVAILABLE);

    log.error("status: {}, exception: {}, stactrace: {}", SERVICE_UNAVAILABLE, exception, exception.getStackTrace());
    return new ResponseEntity<>(apiError, apiError.getHttpStatus());
  }

  @ExceptionHandler({NoResourceFoundException.class, NoSuchElementException.class})
  public ResponseEntity<ApiError> handleNoResourceFoundException(Exception exception, HttpServletRequest request) {
    final ApiError apiError = apiErrorBuilder.buildErrorResponse(exception, NOT_FOUND);

    log.error("status: {}, exception: {}, stactrace: {}", NOT_FOUND, exception, exception.getStackTrace());
    return new ResponseEntity<>(apiError, apiError.getHttpStatus());
  }

  @ExceptionHandler({ValidationException.class, IllegalArgumentException.class, MethodArgumentNotValidException.class,
    HttpMessageNotReadableException.class})
  public ResponseEntity<ApiError> handle(Exception exception, HttpServletRequest request) {
    final ApiError apiError = apiErrorBuilder.buildErrorResponse(exception, BAD_REQUEST);

    log.error("status: {}, exception: {}, stactrace: {}", BAD_REQUEST, exception, exception.getStackTrace());
    return new ResponseEntity<>(apiError, apiError.getHttpStatus());
  }

  @ExceptionHandler({InvalidDataException.class})
  public ResponseEntity<ApiError> handleInvalidDataException(Exception exception, HttpServletRequest request) {
    final ApiError apiError = apiErrorBuilder.buildErrorResponse(exception, BAD_REQUEST);

    log.error("status: {}, exception: {}, stactrace: {}", BAD_REQUEST, exception, exception.getStackTrace());
    return new ResponseEntity<>(apiError, apiError.getHttpStatus());
  }

  @ExceptionHandler({ServiceException.class})
  public ResponseEntity<ApiError> handleServiceException(Exception exception, HttpServletRequest request) {
    final ApiError apiError = apiErrorBuilder.buildErrorResponse(exception, INTERNAL_SERVER_ERROR);

    log.error("status: {}, exception: {}, stactrace: {}", INTERNAL_SERVER_ERROR, exception, exception.getStackTrace());
    return new ResponseEntity<>(apiError, apiError.getHttpStatus());
  }

  @ExceptionHandler({ConstraintViolationException.class})
  public ResponseEntity<ApiError> handleConstraintViolationException(Exception exception, HttpServletRequest request) {
    final ApiError apiError = apiErrorBuilder.buildErrorResponse(exception, BAD_REQUEST);

    log.error("status: {}, exception: {}, stactrace: {}", BAD_REQUEST, exception, exception.getStackTrace());
    return new ResponseEntity<>(apiError, apiError.getHttpStatus());
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<ApiError> handleAllOtherExceptions(Exception exception, HttpServletRequest request) {
    final ApiError apiError = apiErrorBuilder.buildErrorResponse(exception, INTERNAL_SERVER_ERROR);

    log.error("status: {}, exception: {}, stactrace: {}", INTERNAL_SERVER_ERROR, exception, exception.getStackTrace());
    return new ResponseEntity<>(apiError, apiError.getHttpStatus());
  }
}
