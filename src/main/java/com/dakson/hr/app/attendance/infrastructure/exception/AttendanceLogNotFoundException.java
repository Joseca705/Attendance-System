package com.dakson.hr.app.attendance.infrastructure.exception;

public class AttendanceLogNotFoundException extends RuntimeException {

  public AttendanceLogNotFoundException() {
    super("The attendance log was not found.");
  }
}
