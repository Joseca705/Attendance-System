package com.dakson.hr.core.authentication.api.controller;

import com.dakson.hr.common.model.response.BaseResponseDto;
import com.dakson.hr.common.util.CurrentUserJwtUtil;
import com.dakson.hr.core.authentication.api.model.request.ChangePasswordRequestDto;
import com.dakson.hr.core.authentication.api.model.request.LoginRequest;
import com.dakson.hr.core.authentication.api.model.request.SignUpRequestDto;
import com.dakson.hr.core.authentication.api.model.response.AuthenticationResponseDto;
import com.dakson.hr.core.authentication.infrastructure.service.IJwtAuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    @RequestParam(name = "refresh-token") @NotNull(
      message = "Refresh token is required"
    ) UUID refreshToken
  ) {
    AuthenticationResponseDto response =
      this.jwtAuthService.refreshToken(refreshToken);
    return ResponseEntity.ok(response);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/signup")
  public ResponseEntity<AuthenticationResponseDto> signUp(
    @Valid @RequestBody SignUpRequestDto newUser
  ) {
    AuthenticationResponseDto createdUser = this.jwtAuthService.signUp(newUser);
    Integer currentUser = CurrentUserJwtUtil.getCurrentUserId();

    return ResponseEntity.created(
      URI.create(String.format("/api/user/%s", currentUser))
    ).body(createdUser);
  }

  @PreAuthorize("hasRole('USER')")
  @PatchMapping("/change-password")
  public ResponseEntity<BaseResponseDto> changePassword(
    @Valid @RequestBody ChangePasswordRequestDto password
  ) {
    BaseResponseDto response = this.jwtAuthService.changePassword(password);
    return ResponseEntity.ok(response);
  }
}
