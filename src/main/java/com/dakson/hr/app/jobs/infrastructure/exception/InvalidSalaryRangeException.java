package com.dakson.hr.app.jobs.infrastructure.exception;

public class InvalidSalaryRangeException extends RuntimeException {

  public InvalidSalaryRangeException(String message) {
    super(message);
  }
}
