package com.dakson.hr.core.authentication.config;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<Integer> {

  @Override
  @NonNull
  public Optional<Integer> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext()
      .getAuthentication();

    if (
      authentication == null ||
      !authentication.isAuthenticated() ||
      authentication instanceof AnonymousAuthenticationToken
    ) {
      return Optional.empty();
    }

    int userId = (int) authentication.getPrincipal();
    return Optional.of(userId);
  }
}
