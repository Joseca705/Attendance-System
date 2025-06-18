package com.dakson.hr.core.authentication.infrastructure.service;

public interface IRefreshTokenService {
  void deleteExpiredTokens();
}
