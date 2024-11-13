package com.my.weatherapp.exception;

public class InvalidDataException extends RuntimeException {

  public InvalidDataException(String message) {
    super(message);
  }
}
