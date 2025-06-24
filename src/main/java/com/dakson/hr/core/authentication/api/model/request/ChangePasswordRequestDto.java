package com.dakson.hr.core.authentication.api.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequestDto(
  @NotBlank(message = "Password is required")
  @Size(
    min = 8,
    max = 100,
    message = "Password must be between 8 and 100 characters"
  )
  @Pattern(
    regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
    message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
  )
  String password
) {}
