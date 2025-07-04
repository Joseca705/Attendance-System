package com.dakson.hr.common.error_controller;

import com.dakson.hr.common.model.response.error.BaseErrorResponse;
import com.dakson.hr.common.model.response.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class NotFoundToUnauthorizedHandler {

  @ExceptionHandler(
    { NoHandlerFoundException.class, NoResourceFoundException.class }
  )
  public ResponseEntity<Void> handleNotFound(Exception ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<BaseErrorResponse> handleTypeMismatch(
    MethodArgumentTypeMismatchException ex
  ) {
    String message = String.format(
      "Path variable '%s' must be a number, but value '%s' is invalid.",
      ex.getName(),
      ex.getValue()
    );
    var error = ErrorResponse.builder()
      .error(message)
      .status(HttpStatus.BAD_REQUEST.name())
      .code(HttpStatus.BAD_REQUEST.value())
      .build();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }
}
