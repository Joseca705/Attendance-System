package com.dakson.hr.core.authentication.api.controller.error_handler;

import com.dakson.hr.core.authentication.api.model.response.error.BaseErrorResponse;
import com.dakson.hr.core.authentication.api.model.response.error.ErrorResponse;
import com.dakson.hr.core.authentication.infrastructure.exception.ChangePasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class ForbiddenController {

  @ExceptionHandler(exception = ChangePasswordException.class)
  public BaseErrorResponse handleChangePasswordException(
    ChangePasswordException exception
  ) {
    return ErrorResponse.builder()
      .error(exception.getMessage())
      .status(HttpStatus.FORBIDDEN.name())
      .code(HttpStatus.FORBIDDEN.value())
      .build();
  }
}
