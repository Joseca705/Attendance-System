package com.dakson.hr.core.authentication.infrastructure.service;

public interface RefreshTokenService {
  void deleteExpiredTokens();
}
