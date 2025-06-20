package com.dakson.hr.app.attendance.api.controller.error_controller;

import com.dakson.hr.app.attendance.infrastructure.exception.AlreadyCheckedException;
import com.dakson.hr.common.model.response.error.BaseErrorResponse;
import com.dakson.hr.common.model.response.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(code = HttpStatus.CONFLICT)
public class AttendanceConflictErrorController {

  @ExceptionHandler(exception = AlreadyCheckedException.class)
  public BaseErrorResponse handleAlreadyCheckedInException(
    AlreadyCheckedException exception
  ) {
    return ErrorResponse.builder()
      .error(exception.getMessage())
      .status(HttpStatus.CONFLICT.name())
      .code(HttpStatus.CONFLICT.value())
      .build();
  }
}
