package com.dakson.hr.app.location.api.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class CreateDepartmentRequestDto {

  @NotBlank(message = "Name is required")
  @Size(
    min = 3,
    max = 255,
    message = "Name must be between 3 and 255 characters"
  )
  private String name;

  @NotNull(message = "Location is required")
  @Min(value = 1, message = "Location must be greater than 0")
  private Integer locationId;

  @Min(value = 1, message = "Manager must be greater than 0")
  private Integer managerId;
}
