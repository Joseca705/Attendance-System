package com.dakson.hr.app.attendance.domain.dao;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeChecksDao {

  Long id;

  LocalTime checkInTime;

  LocalTime checkOutTime;
}
