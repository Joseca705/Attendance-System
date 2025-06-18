package com.dakson.hr.core.authentication.api.model.response;

import java.util.UUID;

public record AuthenticationResponseDto(String token, UUID refreshToken) {}
