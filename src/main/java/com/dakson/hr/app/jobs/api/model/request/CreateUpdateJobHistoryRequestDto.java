package com.dakson.hr.app.jobs.api.model.request;

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
public class CreateUpdateJobHistoryRequestDto {

  @PastOrPresent(message = "Start date cannot be in the future")
  private LocalDate startDate;

  @Future(message = "End date must be in the future")
  private LocalDate endDate;

  @Positive(message = "Job ID must be positive")
  private Integer jobId;

  @Positive(message = "Department ID must be positive")
  private Integer departmentId;

  @Positive(message = "Employee ID must be positive")
  private Integer employeeId;
}
