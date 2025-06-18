package com.dakson.hr.core.authentication.api.controller.error_handler;

import com.dakson.hr.core.authentication.api.model.response.error.BaseErrorResponse;
import com.dakson.hr.core.authentication.api.model.response.error.ErrorResponse;
import com.dakson.hr.core.authentication.infrastructure.exception.CredentialNotValidException;
import com.dakson.hr.core.authentication.infrastructure.exception.InvalidOrExpiredRefreshTokenExpception;
import com.dakson.hr.core.user.infrastructure.exception.EmailAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestController {

  @ExceptionHandler(exception = UsernameNotFoundException.class)
  public BaseErrorResponse handleUsernameNotFoundException(
    UsernameNotFoundException exception
  ) {
    return ErrorResponse.builder()
      .error(exception.getMessage())
      .status(HttpStatus.BAD_REQUEST.name())
      .code(HttpStatus.BAD_REQUEST.value())
      .build();
  }

  @ExceptionHandler(exception = CredentialNotValidException.class)
  public BaseErrorResponse handleCredentialNotValidException(
    CredentialNotValidException exception
  ) {
    return ErrorResponse.builder()
      .error(exception.getMessage())
      .status(HttpStatus.BAD_REQUEST.name())
      .code(HttpStatus.BAD_REQUEST.value())
      .build();
  }

  @ExceptionHandler(exception = InvalidOrExpiredRefreshTokenExpception.class)
  public BaseErrorResponse handleInvalidOrExpiredRefreshTokenExpception(
    InvalidOrExpiredRefreshTokenExpception exception
  ) {
    return ErrorResponse.builder()
      .error(exception.getMessage())
      .status(HttpStatus.BAD_REQUEST.name())
      .code(HttpStatus.BAD_REQUEST.value())
      .build();
  }

  @ExceptionHandler(exception = EmailAlreadyExistsException.class)
  public BaseErrorResponse handleEmailAlreadyExistsException(
    EmailAlreadyExistsException exception
  ) {
    return ErrorResponse.builder()
      .error(exception.getMessage())
      .status(HttpStatus.BAD_REQUEST.name())
      .code(HttpStatus.BAD_REQUEST.value())
      .build();
  }
}
