package com.dakson.hr.core.authentication.infrastructure.exception;

public class InvalidOrExpiredRefreshTokenExpception extends RuntimeException {

  public InvalidOrExpiredRefreshTokenExpception() {
    super("The given token is invalid or it is expired.");
  }
}
