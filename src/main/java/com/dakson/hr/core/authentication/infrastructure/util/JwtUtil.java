package com.dakson.hr.core.authentication.infrastructure.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  @Value("${application.security.jwt.secret}")
  private String secret;

  @Value("${application.security.jwt.generator}")
  private String userGenerator;

  @Value("${application.security.jwt.expiration}")
  private Long expirationMs;

  public String createToken(Integer userId, String authorities) {
    Algorithm algorithm = Algorithm.HMAC256(this.secret);

    return JWT.create()
      .withIssuer(this.userGenerator)
      .withClaim("authorities", authorities)
      .withClaim("userId", userId)
      .withIssuedAt(new Date())
      .withExpiresAt(new Date(System.currentTimeMillis() + expirationMs))
      .withJWTId(UUID.randomUUID().toString())
      .withNotBefore(new Date(System.currentTimeMillis()))
      .sign(algorithm);
  }

  public DecodedJWT validateToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(this.secret);

      JWTVerifier verifier = JWT.require(algorithm)
        .withIssuer(this.userGenerator)
        .build();

      return verifier.verify(token);
    } catch (JWTVerificationException exception) {
      throw new JWTVerificationException("Token invalid, not Authorized");
    }
  }

  public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
    return decodedJWT.getClaim(claimName);
  }
}
