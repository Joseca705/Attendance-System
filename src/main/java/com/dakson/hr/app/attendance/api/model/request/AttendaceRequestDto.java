package com.dakson.hr.app.attendance.api.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AttendaceRequestDto(
  @NotNull(message = "employee id is required")
  @Positive(message = "employee id should be a positive number")
  Integer employeeId,

  @Size(max = 255, message = "max characters for remarks is 255") String remarks
) {}
