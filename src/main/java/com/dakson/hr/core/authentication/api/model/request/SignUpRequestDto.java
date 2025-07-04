package com.dakson.hr.core.authentication.api.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Request DTO for user registration")
public record SignUpRequestDto(
  @Schema(description = "First name", example = "John")
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

  @Schema(description = "Last name", example = "Doe")
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

  @Schema(description = "Phone number", example = "+1234567890")
  @Pattern(
    regexp = "^\\+?[1-9]\\d{1,14}$",
    message = "Phone number must be a valid international format"
  )
  @NotNull(message = "Phone number is required")
  String phoneNumber,

  @Schema(description = "Email address", example = "john.doe@company.com")
  @NotBlank(message = "Email is required")
  @Email(message = "Email must be a valid email address")
  @Size(max = 100, message = "Email cannot exceed 100 characters")
  @NotNull(message = "Email is required")
  String email,

  @Schema(description = "Hire date", example = "2024-01-15")
  @NotNull(message = "Hire date is required")
  @PastOrPresent(message = "Hire date cannot be in the future")
  LocalDate hireDate,

  // Optional: must be in the future if present
  @Future(message = "End date must be in the future") LocalDate endDate,

  @Schema(description = "Salary", example = "50000.00")
  @Positive(message = "Salary must be a positive number")
  @NotNull(message = "Salary is required")
  BigDecimal salary,

  @Schema(description = "Manager ID", example = "1")
  @Positive(message = "Manager ID must be a positive number")
  @NotNull(message = "Manager ID is required")
  Integer managerId,

  @Schema(description = "Department ID", example = "1")
  @Positive(message = "Department ID must be a positive number")
  @NotNull(message = "Department ID is required")
  Integer departmentId,

  @Schema(description = "Job ID", example = "1")
  @Positive(message = "Job ID must be a positive number")
  @NotNull(message = "Job ID is required")
  Integer jobId
) {}
