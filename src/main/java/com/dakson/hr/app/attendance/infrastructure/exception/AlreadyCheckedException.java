package com.dakson.hr.app.attendance.infrastructure.exception;

public class AlreadyCheckedException extends RuntimeException {

  public AlreadyCheckedException(String message) {
    super(message);
  }
}
