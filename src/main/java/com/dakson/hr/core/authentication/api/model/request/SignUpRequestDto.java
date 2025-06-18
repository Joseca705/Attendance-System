package com.dakson.hr.core.authentication.api.model.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SignUpRequestDto(
  String firstName,
  String lastName,
  String phoneNumber,
  String email,
  LocalDate hireDate,
  BigDecimal salary,
  Integer managerId,
  Integer departmentId
) {}
