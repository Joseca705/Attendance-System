package com.dakson.hr.app.location.api.model.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateLocationRequestDto {

  @Size(
    min = 3,
    max = 100,
    message = "City must be between 3 and 100 characters"
  )
  String city;

  @Size(
    min = 3,
    max = 100,
    message = "Street address must be between 3 and 100 characters"
  )
  String streetAddress;
}
