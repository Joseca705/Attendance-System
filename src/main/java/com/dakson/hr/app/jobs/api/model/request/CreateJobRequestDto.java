package com.dakson.hr.app.jobs.api.model.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateJobRequestDto {

  @NotNull(message = "Job name is required")
  @NotBlank(message = "Job name cannot be empty")
  @Size(
    min = 3,
    max = 100,
    message = "Job name must be between 3 and 100 characters"
  )
  private String name;

  @NotNull(message = "Minimum salary is required")
  @DecimalMin(
    value = "0.0",
    inclusive = false,
    message = "Minimum salary must be greater than 0"
  )
  @DecimalMax(
    value = "999999.99",
    message = "Minimum salary cannot exceed 999,999.99"
  )
  private BigDecimal minSalary;

  @NotNull(message = "Maximum salary is required")
  @DecimalMin(
    value = "0.0",
    inclusive = false,
    message = "Maximum salary must be greater than 0"
  )
  @DecimalMax(
    value = "999999.99",
    message = "Maximum salary cannot exceed 999,999.99"
  )
  private BigDecimal maxSalary;
}
