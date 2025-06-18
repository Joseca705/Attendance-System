package com.dakson.hr.core.authentication.api.controller;

import com.dakson.hr.core.authentication.api.model.request.LoginRequest;
import com.dakson.hr.core.authentication.api.model.response.AuthenticationResponseDto;
import com.dakson.hr.core.authentication.infrastructure.service.IJwtAuthService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final IJwtAuthService jwtAuthService;

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponseDto> handleLogin(
    @Valid @RequestBody LoginRequest loginRequest
  ) {
    AuthenticationResponseDto response =
      this.jwtAuthService.login(loginRequest);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/refresh-token")
  public ResponseEntity<AuthenticationResponseDto> refreshToken(
    @RequestParam(name = "refresh-token") UUID refreshToken
  ) {
    AuthenticationResponseDto response =
      this.jwtAuthService.refreshToken(refreshToken);
    return ResponseEntity.ok(response);
  }
}
