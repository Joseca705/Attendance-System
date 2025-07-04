package com.dakson.hr.app.location.api.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateLocationRequestDto {

  @NotBlank(message = "City is required")
  @Size(
    min = 3,
    max = 100,
    message = "City must be between 3 and 100 characters"
  )
  String city;

  @NotBlank(message = "Street address is required")
  @Size(
    min = 3,
    max = 255,
    message = "Street address must be between 3 and 255 characters"
  )
  String streetAddress;
}
