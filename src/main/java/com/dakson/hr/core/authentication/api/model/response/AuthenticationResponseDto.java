package com.dakson.hr.core.authentication.api.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "Response DTO for authentication operations")
public record AuthenticationResponseDto(
  @Schema(
    description = "JWT token",
    example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  )
  String token,

  @Schema(
    description = "Refresh token",
    example = "123e4567-e89b-12d3-a456-426614174000"
  )
  UUID refreshToken
) {}
