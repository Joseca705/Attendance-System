package com.dakson.hr.app.attendance.api.controller.error_controller;

import com.dakson.hr.app.attendance.infrastructure.exception.AttendanceLogNotFoundException;
import com.dakson.hr.common.model.response.error.BaseErrorResponse;
import com.dakson.hr.common.model.response.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AttendanceNotFoundErrorController {

  @ExceptionHandler(exception = AttendanceLogNotFoundException.class)
  public BaseErrorResponse handleAttendanceLogNotFoundException(
    AttendanceLogNotFoundException exception
  ) {
    return ErrorResponse.builder()
      .error(exception.getMessage())
      .status(HttpStatus.NOT_FOUND.name())
      .code(HttpStatus.NOT_FOUND.value())
      .build();
  }
}
