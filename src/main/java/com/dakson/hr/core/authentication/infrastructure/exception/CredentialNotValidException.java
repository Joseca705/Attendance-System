package com.dakson.hr.core.authentication.infrastructure.exception;

public class CredentialNotValidException extends RuntimeException {

  public CredentialNotValidException() {
    super("The given credentianls are not valid.");
  }
}
