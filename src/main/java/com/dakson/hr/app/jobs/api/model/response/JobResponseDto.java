package com.dakson.hr.app.jobs.api.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "JobResponseDto", description = "DTO representing a job record.")
public class JobResponseDto {

  @Schema(description = "Unique identifier of the job", example = "1")
  private Integer id;

  @Schema(description = "Name of the job", example = "Software Engineer")
  private String name;

  @Schema(description = "Minimum salary for the job", example = "50000.00")
  private BigDecimal minSalary;

  @Schema(description = "Maximum salary for the job", example = "120000.00")
  private BigDecimal maxSalary;
}
