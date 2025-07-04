package com.dakson.hr.app.jobs.api.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
  name = "CreateUpdateJobHistoryRequestDto",
  description = "DTO for creating or updating a job history record. All fields are optional and validated if present."
)
public class CreateUpdateJobHistoryRequestDto {

  @PastOrPresent(message = "Start date cannot be in the future")
  @Schema(
    description = "Start date of the job history record. If provided, must be today or in the past.",
    example = "2023-01-01"
  )
  private LocalDate startDate;

  @Future(message = "End date must be in the future")
  @Schema(
    description = "End date of the job history record. If provided, must be in the future.",
    example = "2025-12-31"
  )
  private LocalDate endDate;

  @Positive(message = "Job ID must be positive")
  @Schema(
    description = "ID of the job. If provided, must be a positive integer.",
    example = "1"
  )
  private Integer jobId;

  @Positive(message = "Department ID must be positive")
  @Schema(
    description = "ID of the department. If provided, must be a positive integer.",
    example = "10"
  )
  private Integer departmentId;

  @Positive(message = "Employee ID must be positive")
  @Schema(
    description = "ID of the employee. If provided, must be a positive integer.",
    example = "100"
  )
  private Integer employeeId;
}
