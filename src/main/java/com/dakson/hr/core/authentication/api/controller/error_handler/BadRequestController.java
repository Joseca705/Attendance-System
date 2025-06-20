package com.dakson.hr.core.authentication.api.controller.error_handler;

import com.dakson.hr.common.model.response.error.BaseErrorResponse;
import com.dakson.hr.common.model.response.error.ErrorResponse;
import com.dakson.hr.common.model.response.error.ErrorsResponse;
import com.dakson.hr.core.authentication.infrastructure.exception.CredentialNotValidException;
import com.dakson.hr.core.authentication.infrastructure.exception.InvalidOrExpiredRefreshTokenExpception;
import com.dakson.hr.core.user.infrastructure.exception.EmailAlreadyExistsException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

  @ExceptionHandler(exception = MethodArgumentNotValidException.class)
  public BaseErrorResponse handleMethodArgumentNotValid(
    MethodArgumentNotValidException ex
  ) {
    List<String> errors = ex
      .getBindingResult()
      .getFieldErrors()
      .stream()
      .map(error -> error.getField() + ": " + error.getDefaultMessage())
      .toList();
    return ErrorsResponse.builder()
      .errors(errors)
      .status(HttpStatus.BAD_REQUEST.name())
      .code(HttpStatus.BAD_REQUEST.value())
      .build();
  }
}
