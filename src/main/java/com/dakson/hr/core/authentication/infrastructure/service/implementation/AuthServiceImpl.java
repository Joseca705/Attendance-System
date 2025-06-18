package com.dakson.hr.core.authentication.infrastructure.service.implementation;

import com.dakson.hr.core.authentication.api.model.request.LoginRequest;
import com.dakson.hr.core.authentication.api.model.request.SignUpRequest;
import com.dakson.hr.core.authentication.api.model.response.AuthenticationResponseDto;
import com.dakson.hr.core.authentication.domain.entity.RefreshTokenEntity;
import com.dakson.hr.core.authentication.domain.repository.RefreshTokenRepository;
import com.dakson.hr.core.authentication.infrastructure.exception.CredentialNotValidException;
import com.dakson.hr.core.authentication.infrastructure.exception.InvalidOrExpiredRefreshTokenExpception;
import com.dakson.hr.core.authentication.infrastructure.service.IJwtAuthService;
import com.dakson.hr.core.authentication.infrastructure.util.JwtUtil;
import com.dakson.hr.core.user.api.model.response.AuthUserFlatDto;
import com.dakson.hr.core.user.domain.repository.UserEntityRepository;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements IJwtAuthService, UserDetailsService {

  private final UserEntityRepository userRepository;
  private final RefreshTokenRepository refreshTokenRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  @Override
  public AuthenticationResponseDto login(LoginRequest request) {
    UserDetails userDetails = this.loadUserByUsername(request.username());

    Integer userId = Integer.parseInt(userDetails.getUsername());
    if (
      !passwordEncoder.matches(request.password(), userDetails.getPassword())
    ) {
      throw new CredentialNotValidException();
    }

    UsernamePasswordAuthenticationToken authentication =
      new UsernamePasswordAuthenticationToken(
        userId,
        userDetails.getPassword(),
        userDetails.getAuthorities()
      );

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String authorities = authentication
      .getAuthorities()
      .stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.joining(","));

    String token = jwtUtil.createToken(userId, authorities);

    RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
    refreshTokenEntity.setGrantId(userId);
    refreshTokenEntity.setExpiresAt(Instant.now().plusSeconds(3600));
    this.refreshTokenRepository.save(refreshTokenEntity);

    return new AuthenticationResponseDto(token, refreshTokenEntity.getId());
  }

  @Override
  public String signUp(SignUpRequest request) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'signUp'");
  }

  @Override
  public String logout() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'logout'");
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    List<AuthUserFlatDto> userFlat =
      this.userRepository.findFlatByUsername(username);

    if (userFlat.isEmpty()) throw new UsernameNotFoundException(
      String.format("User with username %s was not found.", username)
    );

    AuthUserFlatDto simpleUser = userFlat.get(0);
    List<SimpleGrantedAuthority> authorities = userFlat
      .stream()
      .map(role ->
        new SimpleGrantedAuthority(
          String.format("ROLE_%s", role.getRole().toString())
        )
      )
      .toList();

    return User.builder()
      .username(String.valueOf(simpleUser.getId()))
      .password(simpleUser.getPassword())
      .authorities(authorities)
      .build();
  }

  @Override
  public AuthenticationResponseDto refreshToken(UUID refreshToken) {
    final RefreshTokenEntity refreshTokenEntity =
      this.refreshTokenRepository.findByIdAndExpiresAtAfter(
          refreshToken,
          Instant.now()
        ).orElseThrow(() -> new InvalidOrExpiredRefreshTokenExpception());

    List<AuthUserFlatDto> userFlat =
      this.userRepository.findFlatByUserId(refreshTokenEntity.getGrantId());
    String authorities = userFlat
      .stream()
      .map(role -> role.getRole().name())
      .collect(Collectors.joining(","));

    final String refreshedToken =
      this.jwtUtil.createToken(refreshTokenEntity.getGrantId(), authorities);

    return new AuthenticationResponseDto(refreshedToken, refreshToken);
  }
}
