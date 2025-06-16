package com.dakson.hr.core.authentication.config.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.dakson.hr.core.authentication.infrastructure.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(
    @NonNull HttpServletRequest request,
    @NonNull HttpServletResponse response,
    @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (jwtToken != null) {
      jwtToken = jwtToken.substring(7);

      DecodedJWT decodedJWT = jwtUtil.validateToken(jwtToken);

      Integer userId = jwtUtil.getSpecificClaim(decodedJWT, "userId").asInt();
      String stringAuthorities = jwtUtil
        .getSpecificClaim(decodedJWT, "authorities")
        .asString();

      Collection<? extends GrantedAuthority> authorities =
        AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities);

      SecurityContext context = SecurityContextHolder.createEmptyContext();
      Authentication authenticationToken =
        new UsernamePasswordAuthenticationToken(userId, null, authorities);
      context.setAuthentication(authenticationToken);
      SecurityContextHolder.setContext(context);
    }
    filterChain.doFilter(request, response);
  }
}
