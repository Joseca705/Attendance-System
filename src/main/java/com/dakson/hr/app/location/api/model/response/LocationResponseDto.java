package com.dakson.hr.app.location.api.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
  name = "LocationResponseDto",
  description = "DTO representing a location."
)
public class LocationResponseDto {

  @Schema(description = "Unique identifier of the location", example = "10")
  Integer id;

  @Schema(description = "City of the location", example = "New York")
  String city;

  @Schema(
    description = "Street address of the location",
    example = "123 Main St"
  )
  String streetAddress;
}
