package com.dakson.hr.core.authentication.api.controller;

import com.dakson.hr.core.authentication.api.model.request.LoginRequest;
import com.dakson.hr.core.authentication.api.model.response.LoginResponse;
import com.dakson.hr.core.authentication.infrastructure.service.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final IAuthService authService;

  @PostMapping("/login")
  private ResponseEntity<LoginResponse> handleLogin(
    @Valid @RequestBody LoginRequest loginRequest
  ) {
    LoginResponse response = this.authService.login(loginRequest);
    return ResponseEntity.ok(response);
  }
}
