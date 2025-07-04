package com.dakson.hr.core.authentication.infrastructure.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class RefreshTokenCleanupScheduler {

  private final RefreshTokenService refreshTokenService;

  @Scheduled(cron = "0 0 0 * * *")
  public void scheduledDeleteExpiredTokens() {
    refreshTokenService.deleteExpiredTokens();
  }
}
