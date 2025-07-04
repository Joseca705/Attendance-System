package com.dakson.hr.app.location.api.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
  name = "DepartmentResponseDto",
  description = "DTO representing a department."
)
public class DepartmentResponseDto {

  private Integer id;

  @Schema(description = "Name of the department", example = "Engineering")
  private String name;

  @Schema(description = "Manager of the department")
  private EmployeeDepartmentResponseDto manager;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
  name = "EmployeeDepartmentResponseDto",
  description = "DTO representing a department manager."
)
class EmployeeDepartmentResponseDto {

  private Integer id;

  @Schema(description = "First name of the employee", example = "John")
  private String firstName;

  @Schema(description = "Last name of the employee", example = "Doe")
  private String lastName;
}
