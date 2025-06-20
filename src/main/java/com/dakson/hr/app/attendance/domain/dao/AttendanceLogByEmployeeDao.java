package com.dakson.hr.app.attendance.domain.dao;

import com.dakson.hr.app.attendance.domain.constant.AttendaceStatus;
import java.time.LocalDate;
import java.time.LocalTime;

public interface AttendanceLogByEmployeeDao {
  Long getId();

  LocalDate getLogDate();

  LocalTime getCheckInTime();

  LocalTime getCheckOutTime();

  AttendaceStatus getStatus();

  String getRemarks();
}
