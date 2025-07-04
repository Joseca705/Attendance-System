package com.dakson.hr.app.jobs.api.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
  name = "JobHistoryResponseDto",
  description = "DTO representing a job history record."
)
public class JobHistoryResponseDto {

  @Schema(
    description = "Unique identifier of the job history record",
    example = "10"
  )
  private Integer id;

  @Schema(
    description = "Start date of the job history record",
    example = "2023-01-01"
  )
  private LocalDate startDate;

  @Schema(
    description = "End date of the job history record",
    example = "2023-12-31"
  )
  private LocalDate endDate;
}
