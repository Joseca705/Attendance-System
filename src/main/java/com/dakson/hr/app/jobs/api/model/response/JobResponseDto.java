package com.dakson.hr.app.jobs.api.model.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobResponseDto {

  private Integer id;

  private String name;

  private BigDecimal minSalary;

  private BigDecimal maxSalary;
}
