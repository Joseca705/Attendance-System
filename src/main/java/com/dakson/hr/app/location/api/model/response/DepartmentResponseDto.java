package com.dakson.hr.app.location.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentResponseDto {

  private Integer id;

  private String name;

  private EmployeeDepartmentResponseDto manager;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class EmployeeDepartmentResponseDto {

  private Integer id;

  private String firstName;

  private String lastName;
}
