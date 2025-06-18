package com.dakson.hr.core.authentication.api.model.request;

public record SignUpRequestDto(
  String firstName,
  String lastName,
  String email,
  String password
) {}
