package com.dakson.hr.core.authentication.api.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public record SignUpRequestDto(
  @NotBlank(message = "First name is required")
  @Size(
    min = 2,
    max = 50,
    message = "First name must be between 2 and 50 characters"
  )
  @Pattern(
    regexp = "^[a-zA-Z\\s]+$",
    message = "First name can only contain letters and spaces"
  )
  String firstName,

  @NotBlank(message = "Last name is required")
  @Size(
    min = 2,
    max = 50,
    message = "Last name must be between 2 and 50 characters"
  )
  @Pattern(
    regexp = "^[a-zA-Z\\s]+$",
    message = "Last name can only contain letters and spaces"
  )
  String lastName,

  @Pattern(
    regexp = "^\\+?[1-9]\\d{1,14}$",
    message = "Phone number must be a valid international format"
  )
  String phoneNumber,

  @NotBlank(message = "Email is required")
  @Email(message = "Email must be a valid email address")
  @Size(max = 100, message = "Email cannot exceed 100 characters")
  String email,

  @NotNull(message = "Hire date is required")
  @PastOrPresent(message = "Hire date cannot be in the future")
  LocalDate hireDate,

  @Positive(message = "Salary must be a positive number") BigDecimal salary,

  @Positive(message = "Manager ID must be a positive number") Integer managerId,

  @Positive(message = "Department ID must be a positive number")
  Integer departmentId,

  @Positive(message = "Job ID must be a positive number") Integer jobId
) {}
