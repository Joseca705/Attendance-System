package com.dakson.hr.core.authentication.domain.repository;

import com.dakson.hr.core.authentication.domain.entity.RefreshTokenEntity;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository
  extends JpaRepository<RefreshTokenEntity, UUID> {
  Optional<RefreshTokenEntity> findByIdAndExpiresAtAfter(UUID id, Instant date);

  @Modifying
  @Query(
    """
    DELETE FROM RefreshTokenEntity rt
    WHERE rt.expiresAt < CURRENT_TIMESTAMP
    """
  )
  void deleteExpiredTokens();
}
