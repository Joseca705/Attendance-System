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

  private Employee manager;

  private Location location;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Employee {

    private Integer id;
    private String firstName;
    private String lastName;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Location {

    private Integer id;
    private String name;
  }
}
