package com.dakson.hr.app.location.api.controller.error_handler;

import com.dakson.hr.app.location.infrastructure.exception.AlreadyAssignedException;
import com.dakson.hr.app.location.infrastructure.exception.AlreadyAssignedLocationException;
import com.dakson.hr.app.location.infrastructure.exception.ManagerAlreadyAssignedToDepartmentException;
import com.dakson.hr.app.location.infrastructure.exception.UserNotFoundException;
import com.dakson.hr.common.exception.ResourceNotFoundException;
import com.dakson.hr.common.model.response.error.BaseErrorResponse;
import com.dakson.hr.common.model.response.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class LocationBadRequestController {

  @ExceptionHandler(ResourceNotFoundException.class)
  public BaseErrorResponse handleResourceNotFoundException(
    ResourceNotFoundException ex
  ) {
    return ErrorResponse.builder()
      .error(ex.getMessage())
      .status(HttpStatus.BAD_REQUEST.name())
      .code(HttpStatus.BAD_REQUEST.value())
      .build();
  }

  @ExceptionHandler(UserNotFoundException.class)
  public BaseErrorResponse handleUserNotFoundException(
    UserNotFoundException ex
  ) {
    return ErrorResponse.builder()
      .error(ex.getMessage())
      .status(HttpStatus.BAD_REQUEST.name())
      .code(HttpStatus.BAD_REQUEST.value())
      .build();
  }

  @ExceptionHandler(AlreadyAssignedException.class)
  public BaseErrorResponse handleAlreadyAssignedException(
    AlreadyAssignedException ex
  ) {
    return ErrorResponse.builder()
      .error(ex.getMessage())
      .status(HttpStatus.BAD_REQUEST.name())
      .code(HttpStatus.BAD_REQUEST.value())
      .build();
  }

  @ExceptionHandler(AlreadyAssignedLocationException.class)
  public BaseErrorResponse handleAlreadyAssignedLocationException(
    AlreadyAssignedLocationException ex
  ) {
    return ErrorResponse.builder()
      .error(ex.getMessage())
      .status(HttpStatus.BAD_REQUEST.name())
      .code(HttpStatus.BAD_REQUEST.value())
      .build();
  }

  @ExceptionHandler(ManagerAlreadyAssignedToDepartmentException.class)
  public BaseErrorResponse handleManagerAlreadyAssignedToDepartmentException(
    ManagerAlreadyAssignedToDepartmentException ex
  ) {
    return ErrorResponse.builder()
      .error(ex.getMessage())
      .status(HttpStatus.BAD_REQUEST.name())
      .code(HttpStatus.BAD_REQUEST.value())
      .build();
  }
}
