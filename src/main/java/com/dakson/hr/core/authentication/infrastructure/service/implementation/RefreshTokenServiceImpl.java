package com.dakson.hr.core.authentication.infrastructure.service.implementation;

import com.dakson.hr.core.authentication.domain.repository.RefreshTokenRepository;
import com.dakson.hr.core.authentication.infrastructure.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

  private final RefreshTokenRepository refreshTokenRepository;

  @Override
  public void deleteExpiredTokens() {
    this.refreshTokenRepository.deleteExpiredTokens();
  }
}
