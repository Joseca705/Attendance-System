package com.dakson.hr.app.location.infrastructure.exception;

public class AlreadyAssignedLocationException extends RuntimeException {

  public AlreadyAssignedLocationException(String message) {
    super(message);
  }
}
