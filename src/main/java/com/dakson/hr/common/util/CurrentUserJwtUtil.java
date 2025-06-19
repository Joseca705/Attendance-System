package com.dakson.hr.common.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUserJwtUtil {

  public static Integer getCurrentUserId() {
    Authentication authentication = SecurityContextHolder.getContext()
      .getAuthentication();

    if (
      authentication == null ||
      !authentication.isAuthenticated() ||
      authentication instanceof AnonymousAuthenticationToken
    ) {
      return null;
    }

    int userId = (int) authentication.getPrincipal();
    return userId;
  }
}
