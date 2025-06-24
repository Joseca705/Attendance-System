package com.dakson.hr.app.attendance.api.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

public record AttendaceRequestDto(
  @NotNull(message = "employee id is required")
  @Positive(message = "employee id should be a positive number")
  Long employeeId,

  @NotNull(message = "timestamp is required")
  @PastOrPresent(message = "Timestamp cannot be in the future")
  LocalDateTime timestamp
) {}
