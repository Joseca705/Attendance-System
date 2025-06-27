package com.dakson.hr.app.location.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailedDepartmentResponseDto {

  private Integer id;

  private String name;

  private EmployeeDetailedDepartmentResponseDto manager;

  private DetailedLocationResponseDto location;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class EmployeeDetailedDepartmentResponseDto {

  private Integer id;

  private String firstName;

  private String lastName;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class DetailedLocationResponseDto {

  private Integer id;

  private String name;
}
