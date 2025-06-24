package com.dakson.hr.app.attendance.domain.dao;

import com.dakson.hr.app.attendance.domain.constant.AttendaceStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalTime;

@Schema(description = "DAO for attendance log data by employee")
public interface AttendanceLogByEmployeeDao {
  @Schema(description = "Attendance log ID", example = "1")
  Long getId();

  @Schema(description = "Log date", example = "2024-01-15")
  LocalDate getLogDate();

  @Schema(description = "Check-in time", example = "09:00:00")
  LocalTime getCheckInTime();

  @Schema(description = "Check-out time", example = "17:00:00")
  LocalTime getCheckOutTime();

  @Schema(description = "Attendance status", example = "CHECKED_IN")
  AttendaceStatus getStatus();

  @Schema(description = "Remarks", example = "On time")
  String getRemarks();
}
