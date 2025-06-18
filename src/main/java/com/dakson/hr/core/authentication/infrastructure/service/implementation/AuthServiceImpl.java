package com.dakson.hr.core.authentication.infrastructure.service.implementation;

import com.dakson.hr.core.authentication.api.model.request.LoginRequest;
import com.dakson.hr.core.authentication.api.model.request.SignUpRequestDto;
import com.dakson.hr.core.authentication.api.model.response.AuthenticationResponseDto;
import com.dakson.hr.core.authentication.domain.entity.RefreshTokenEntity;
import com.dakson.hr.core.authentication.domain.repository.RefreshTokenRepository;
import com.dakson.hr.core.authentication.infrastructure.exception.CredentialNotValidException;
import com.dakson.hr.core.authentication.infrastructure.exception.InvalidOrExpiredRefreshTokenExpception;
import com.dakson.hr.core.authentication.infrastructure.service.IJwtAuthService;
import com.dakson.hr.core.authentication.infrastructure.util.JwtUtil;
import com.dakson.hr.core.authorization.domain.constant.Role;
import com.dakson.hr.core.authorization.domain.entity.RoleEntity;
import com.dakson.hr.core.authorization.domain.entity.UserRoleEntity;
import com.dakson.hr.core.authorization.domain.repository.UserRoleEntityRepository;
import com.dakson.hr.core.user.api.model.response.AuthUserFlatDto;
import com.dakson.hr.core.user.domain.entity.PersonEntity;
import com.dakson.hr.core.user.domain.entity.UserEntity;
import com.dakson.hr.core.user.domain.repository.PersonEntityRepository;
import com.dakson.hr.core.user.domain.repository.UserEntityRepository;
import com.dakson.hr.core.user.infrastructure.exception.EmailAlreadyExistsException;
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
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements IJwtAuthService, UserDetailsService {

  private final UserEntityRepository userRepository;
  private final PersonEntityRepository personRepository;
  private final UserRoleEntityRepository userRoleRepository;
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
  @Transactional
  public AuthenticationResponseDto signUp(SignUpRequestDto request) {
    boolean existsEmail = this.userRepository.existsByEmail(request.email());
    if (existsEmail) throw new EmailAlreadyExistsException();

    PersonEntity createdPerson = new PersonEntity(
      request.firstName(),
      request.lastName()
    );
    createdPerson.setCreatedBy(0);
    this.personRepository.save(createdPerson);

    UserEntity createdUser = new UserEntity(
      request.email(),
      passwordEncoder.encode(request.password()),
      request.email(),
      createdPerson
    );
    createdUser.setCreatedBy(0);
    this.userRepository.save(createdUser);

    UserRoleEntity userRole = new UserRoleEntity(
      new RoleEntity(2),
      createdUser
    );
    userRole.setCreatedBy(0);
    this.userRoleRepository.save(userRole);

    List<SimpleGrantedAuthority> authority = List.of(
      new SimpleGrantedAuthority(Role.USER.name())
    );

    UsernamePasswordAuthenticationToken authentication =
      new UsernamePasswordAuthenticationToken(
        createdUser.getId(),
        createdUser.getPassword(),
        authority
      );
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String token = jwtUtil.createToken(createdUser.getId(), Role.USER.name());

    RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
    refreshTokenEntity.setGrantId(createdUser.getId());
    refreshTokenEntity.setExpiresAt(Instant.now().plusSeconds(3600));
    this.refreshTokenRepository.save(refreshTokenEntity);

    return new AuthenticationResponseDto(token, refreshTokenEntity.getId());
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
