package com.dakson.hr.core.user.infrastructure.exception;

public class EmailAlreadyExistsException extends RuntimeException {

  public EmailAlreadyExistsException() {
    super("The given email already exists.");
  }
}
