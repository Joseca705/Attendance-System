package com.dakson.hr.app.location.api.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
  name = "DetailedDepartmentResponseDto",
  description = "DTO representing a department with manager and location details."
)
public class DetailedDepartmentResponseDto {

  private Integer id;

  private String name;

  @Schema(description = "Manager of the department")
  private Employee manager;

  @Schema(description = "Location of the department")
  private Location location;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Schema(
    name = "Employee",
    description = "DTO representing the department manager."
  )
  public static class Employee {

    private Integer id;

    @Schema(description = "First name of the employee", example = "John")
    private String firstName;

    @Schema(description = "Last name of the employee", example = "Doe")
    private String lastName;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Schema(
    name = "Location",
    description = "DTO representing the department location."
  )
  public static class Location {

    private Integer id;

    @Schema(description = "Name of the location", example = "Headquarters")
    private String name;
  }
}
