package com.dakson.hr.core.authentication.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "refresh_tokens")
public class RefreshTokenEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "grant_id", nullable = false)
  private Integer grantId;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private LocalDateTime iat;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "expires_at", nullable = false)
  private LocalDateTime expiresAt;

  @Column(name = "is_reboked", nullable = false)
  private Boolean isRevoked;

  private String data;
}
