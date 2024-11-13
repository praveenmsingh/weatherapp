package com.my.weatherapp.exception;

public class ServiceException extends RuntimeException {

  private final static long serialVersionUID = 346237846492576389L;

  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
