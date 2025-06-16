package com.dakson.hr.core.authentication.api.model.request;

public record SignUpRequest(
  String firstName,
  String lastName,
  String email,
  String username,
  String password
) {}
