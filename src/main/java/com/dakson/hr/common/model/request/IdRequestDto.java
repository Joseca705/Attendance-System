package com.dakson.hr.common.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class IdRequestDto {

  @NotNull(message = "ID is required")
  @Min(value = 1, message = "ID must be greater than 0")
  private Integer id;
}
