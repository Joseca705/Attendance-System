package com.dakson.hr.core.authentication.infrastructure.service.implementation;

import com.dakson.hr.app.jobs.api.model.request.CreateUpdateJobHistoryRequestDto;
import com.dakson.hr.app.jobs.domain.entity.Job;
import com.dakson.hr.app.jobs.infrastructure.service.JobHistoryService;
import com.dakson.hr.app.location.domain.entity.Department;
import com.dakson.hr.common.model.response.BaseResponseDto;
import com.dakson.hr.common.util.CurrentUserJwtUtil;
import com.dakson.hr.core.authentication.api.model.request.ChangePasswordRequestDto;
import com.dakson.hr.core.authentication.api.model.request.LoginRequest;
import com.dakson.hr.core.authentication.api.model.request.SignUpRequestDto;
import com.dakson.hr.core.authentication.api.model.response.AuthenticationResponseDto;
import com.dakson.hr.core.authentication.domain.entity.RefreshTokenEntity;
import com.dakson.hr.core.authentication.domain.repository.RefreshTokenRepository;
import com.dakson.hr.core.authentication.infrastructure.exception.CredentialNotValidException;
import com.dakson.hr.core.authentication.infrastructure.exception.InvalidOrExpiredRefreshTokenExpception;
import com.dakson.hr.core.authentication.infrastructure.service.JwtAuthService;
import com.dakson.hr.core.authentication.infrastructure.util.JwtUtil;
import com.dakson.hr.core.authorization.domain.constant.Role;
import com.dakson.hr.core.authorization.domain.entity.RoleEntity;
import com.dakson.hr.core.authorization.domain.entity.UserRoleEntity;
import com.dakson.hr.core.authorization.domain.repository.UserRoleEntityRepository;
import com.dakson.hr.core.user.api.model.response.AuthUserFlatDto;
import com.dakson.hr.core.user.domain.entity.Employee;
import com.dakson.hr.core.user.domain.entity.UserEntity;
import com.dakson.hr.core.user.domain.repository.EmployeeRepository;
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
public class AuthServiceImpl implements JwtAuthService, UserDetailsService {

  private final UserEntityRepository userRepository;
  private final EmployeeRepository employeeRepository;
  private final UserRoleEntityRepository userRoleRepository;
  private final RefreshTokenRepository refreshTokenRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final JobHistoryService jobHistoryService;

  private final String DEFAULT_PASSWORD = "123456789";

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
    boolean existsEmail =
      this.employeeRepository.existsByEmail(request.email());
    if (existsEmail) throw new EmailAlreadyExistsException();

    Employee createdeEmployee = new Employee(
      request.firstName(),
      request.lastName(),
      request.phoneNumber(),
      request.email(),
      request.hireDate(),
      request.salary(),
      new Department(request.departmentId()),
      new Employee(request.managerId()),
      new Job(request.jobId())
    );
    this.employeeRepository.save(createdeEmployee);

    // Create JobHistory for the new employee
    CreateUpdateJobHistoryRequestDto jobHistoryRequest =
      CreateUpdateJobHistoryRequestDto.builder()
        .employeeId(createdeEmployee.getId())
        .jobId(request.jobId())
        .departmentId(request.departmentId())
        .startDate(request.hireDate())
        .endDate(request.endDate())
        .build();
    jobHistoryService.create(jobHistoryRequest);

    UserEntity createdUser = new UserEntity(
      request.email(),
      passwordEncoder.encode(DEFAULT_PASSWORD),
      createdeEmployee
    );
    this.userRepository.save(createdUser);

    UserRoleEntity roleUser = new UserRoleEntity(
      new RoleEntity(2),
      createdUser
    );
    this.userRoleRepository.save(roleUser);

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
  public BaseResponseDto logout() {
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

  @Override
  @Transactional
  public BaseResponseDto changePassword(ChangePasswordRequestDto request) {
    Integer currentUser = CurrentUserJwtUtil.getCurrentUserId();
    this.userRepository.updateUserPassword(
        passwordEncoder.encode(request.password()),
        currentUser
      );
    return new BaseResponseDto("Password changed successfully.");
  }
}
