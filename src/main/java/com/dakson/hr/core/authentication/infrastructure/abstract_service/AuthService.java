package com.dakson.hr.core.authentication.infrastructure.abstract_service;

public interface AuthService<RQLogin, RQSignup, RSLogin, RS> {
  RSLogin login(RQLogin request);

  RSLogin signUp(RQSignup request);

  RS logout();
}
