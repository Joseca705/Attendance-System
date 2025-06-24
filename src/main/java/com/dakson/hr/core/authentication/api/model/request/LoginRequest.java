package com.dakson.hr.core.authentication.api.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request DTO for user login")
public record LoginRequest(
  @Schema(description = "Username", example = "john.doe")
  @NotBlank(message = "Username is required")
  @Size(
    min = 3,
    max = 50,
    message = "Username must be between 3 and 50 characters"
  )
  String username,

  @Schema(description = "Password", example = "password123")
  @NotBlank(message = "Password is required")
  @Size(
    min = 6,
    max = 100,
    message = "Password must be between 6 and 100 characters"
  )
  String password
) {}
