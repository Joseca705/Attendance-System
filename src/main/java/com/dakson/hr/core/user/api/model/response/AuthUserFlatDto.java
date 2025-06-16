package com.dakson.hr.core.user.api.model.response;

import com.dakson.hr.core.authorization.domain.constant.Role;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class AuthUserFlatDto {

  private Integer id;

  private String password;

  private Role role;
}
