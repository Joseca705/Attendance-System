package com.dakson.hr.core.authentication.infrastructure.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  @Value("${application.security.jwt.key}")
  private String privateKey;

  @Value("${application.security.jwt.generator}")
  private String userGenerator;

  private static final String SECRET = "my-super-secret"; // 🔐 Use env variable in real apps
  private static final long EXPIRATION_MS = 86400000; // 1 day

  private final Algorithm algorithm = Algorithm.HMAC256(SECRET);

  public String createToken(Authentication authentication) {
    Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
    int userId = (int) authentication.getPrincipal();
    String authorities = authentication
      .getAuthorities()
      .stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.joining(","));

    return JWT.create()
      .withIssuer(this.userGenerator)
      .withClaim("authorities", authorities)
      .withClaim("userId", userId)
      .withIssuedAt(new Date())
      .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))
      .withJWTId(UUID.randomUUID().toString())
      .withNotBefore(new Date(System.currentTimeMillis()))
      .sign(algorithm);
  }

  public DecodedJWT validateToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

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
