package com.dakson.hr.app.location.infrastructure.exception;

public class ManagerAlreadyAssignedToDepartmentException
  extends RuntimeException {

  public ManagerAlreadyAssignedToDepartmentException(String message) {
    super(message);
  }
}
