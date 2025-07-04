package com.dakson.hr.app.attendance.api.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

@Schema(description = "Request DTO for attendance check-in/check-out")
public record AttendaceRequestDto(
  @Schema(description = "Employee ID", example = "123")
  @NotNull(message = "employee id is required")
  @Positive(message = "employee id should be a positive number")
  Long employeeId,

  @Schema(
    description = "Timestamp of check-in/check-out",
    example = "2024-01-15T09:30:00"
  )
  @NotNull(message = "timestamp is required")
  @PastOrPresent(message = "Timestamp cannot be in the future")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  LocalDateTime timestamp
) {}
