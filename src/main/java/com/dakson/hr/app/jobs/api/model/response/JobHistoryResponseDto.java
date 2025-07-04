package com.dakson.hr.app.jobs.api.model.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobHistoryResponseDto {

  private Integer id;

  private LocalDate startDate;

  private LocalDate endDate;
}
