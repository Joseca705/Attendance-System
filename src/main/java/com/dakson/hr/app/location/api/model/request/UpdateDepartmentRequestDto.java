package com.dakson.hr.app.location.api.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDepartmentRequestDto {

  @NotBlank(message = "Name is required")
  @Size(
    min = 3,
    max = 255,
    message = "Name must be between 3 and 255 characters"
  )
  private String name;
}
