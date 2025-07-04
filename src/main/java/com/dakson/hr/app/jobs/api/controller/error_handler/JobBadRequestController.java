package com.dakson.hr.app.jobs.api.controller.error_handler;

import com.dakson.hr.app.jobs.infrastructure.exception.InvalidSalaryRangeException;
import com.dakson.hr.common.model.response.error.BaseErrorResponse;
import com.dakson.hr.common.model.response.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class JobBadRequestController {

  @ExceptionHandler(InvalidSalaryRangeException.class)
  public BaseErrorResponse handleInvalidSalaryRangeException(
    InvalidSalaryRangeException ex
  ) {
    return ErrorResponse.builder()
      .error(ex.getMessage())
      .status(HttpStatus.BAD_REQUEST.name())
      .code(HttpStatus.BAD_REQUEST.value())
      .build();
  }
}
