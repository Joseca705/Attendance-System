package com.dakson.hr.app.location.infrastructure.exception;

public class AlreadyAssignedException extends RuntimeException {

  public AlreadyAssignedException(String message) {
    super(message);
  }
}
