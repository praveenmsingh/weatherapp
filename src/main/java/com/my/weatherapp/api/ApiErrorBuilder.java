package com.my.weatherapp.api;

import com.my.weatherapp.exception.InvalidDataException;
import jakarta.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Component
public class ApiErrorBuilder {

  public ApiError buildErrorResponse(Exception exception, HttpStatus status) {
    return ApiError.builder()
        .httpStatus(status)
        .errorId(status.name())
        .message(status.getReasonPhrase())
        .details(buildDetails(exception, status))
        .build();
  }

  private List<ApiErrorDetail> buildDetails(Exception exception, HttpStatus httpStatus) {
    if (exception instanceof ConstraintViolationException constraintViolationException) {
      return buildDetails(constraintViolationException);
    } else if (exception instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
      return buildDetails(methodArgumentNotValidException);
    } else if (exception instanceof InvalidDataException invalidDataException) {
      return buildDetails(invalidDataException);
    } else if (exception instanceof NoSuchElementException noSuchElementException) {
      return buildDetails(noSuchElementException);
    } else {
      return buildDetails(httpStatus);
    }
  }

  private List<ApiErrorDetail> buildDetails(NoSuchElementException exception) {
    return Collections.singletonList(ApiErrorDetail.builder()
        .issue(exception.getMessage())
        .build());
  }

  // For non-specific errors
  private List<ApiErrorDetail> buildDetails(HttpStatus status) {
    return Collections.singletonList(ApiErrorDetail.builder()
        .issue(status.name())
        .build());
  }

  private List<ApiErrorDetail> buildDetails(ConstraintViolationException exception) {
    return exception.getConstraintViolations().stream()
        .map(violation -> ApiErrorDetail.builder()
            .field(violation.getPropertyPath().toString())
            .issue(violation.getMessage())
            .build())
        .toList();
  }

  private List<ApiErrorDetail> buildDetails(InvalidDataException exception) {
    return Collections.singletonList(ApiErrorDetail.builder()
        .issue(exception.getMessage())
        .build());
  }

  private List<ApiErrorDetail> buildDetails(MethodArgumentNotValidException exception) {
    return exception.getBindingResult().getAllErrors().stream().map(err ->
        ApiErrorDetail.builder()
            .field((err instanceof FieldError fieldError) ? fieldError.getField() : err.getObjectName())
            .issue(err.getDefaultMessage())
            .build()
        ).toList();
  }
}
