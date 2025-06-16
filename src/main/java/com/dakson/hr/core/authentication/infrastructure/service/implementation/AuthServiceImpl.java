package com.dakson.hr.core.authentication.infrastructure.service.implementation;

import com.dakson.hr.core.authentication.api.model.request.LoginRequest;
import com.dakson.hr.core.authentication.api.model.request.SignUpRequest;
import com.dakson.hr.core.authentication.api.model.response.LoginResponse;
import com.dakson.hr.core.authentication.infrastructure.exception.CredentialNotValidException;
import com.dakson.hr.core.authentication.infrastructure.service.IAuthService;
import com.dakson.hr.core.authentication.infrastructure.util.JwtUtil;
import com.dakson.hr.core.user.api.model.response.AuthUserFlatDto;
import com.dakson.hr.core.user.domain.repository.UserEntityRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
public class AuthServiceImpl implements IAuthService, UserDetailsService {

  private final UserEntityRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  @Override
  public LoginResponse login(LoginRequest request) {
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

    String token = jwtUtil.createToken(authentication);
    return new LoginResponse(token, "");
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
    List<AuthUserFlatDto> userFlat = userRepository.findingFlatByUsername(
      username
    );

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
}
