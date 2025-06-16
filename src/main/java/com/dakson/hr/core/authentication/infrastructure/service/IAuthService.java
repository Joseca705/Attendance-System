package com.dakson.hr.core.authentication.infrastructure.service;

import com.dakson.hr.core.authentication.api.model.request.LoginRequest;
import com.dakson.hr.core.authentication.api.model.request.SignUpRequest;
import com.dakson.hr.core.authentication.api.model.response.LoginResponse;
import com.dakson.hr.core.authentication.infrastructure.abstract_service.AuthService;

public interface IAuthService
  extends AuthService<LoginRequest, SignUpRequest, LoginResponse, String> {}
