package com.dakson.hr.app.location.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailedDepartmentDao {

  private Integer id;
  private String name;
  private Integer managerId;
  private String firstName;
  private String lastName;
  private Integer locationId;
  private String locationName;
}
