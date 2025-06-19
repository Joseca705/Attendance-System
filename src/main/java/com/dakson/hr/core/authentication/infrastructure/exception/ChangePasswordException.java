package com.dakson.hr.core.authentication.infrastructure.exception;

public class ChangePasswordException extends RuntimeException {

  public ChangePasswordException() {
    super("You must change your password");
  }
}
