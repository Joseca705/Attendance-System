package com.dakson.hr.core.authentication.infrastructure.service;

import com.dakson.hr.common.model.response.BaseResponseDto;
import com.dakson.hr.core.authentication.api.model.request.ChangePasswordRequestDto;
import com.dakson.hr.core.authentication.api.model.request.LoginRequest;
import com.dakson.hr.core.authentication.api.model.request.SignUpRequestDto;
import com.dakson.hr.core.authentication.api.model.response.AuthenticationResponseDto;
import com.dakson.hr.core.authentication.infrastructure.abstract_service.AuthService;
import java.util.UUID;

public interface IJwtAuthService
  extends
    AuthService<
      LoginRequest,
      SignUpRequestDto,
      ChangePasswordRequestDto,
      AuthenticationResponseDto,
      BaseResponseDto
    > {
  AuthenticationResponseDto refreshToken(UUID id);
}
