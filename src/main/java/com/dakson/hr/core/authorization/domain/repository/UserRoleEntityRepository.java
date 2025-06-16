package com.dakson.hr.core.authorization.domain.repository;

import com.dakson.hr.core.authorization.domain.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleEntityRepository
  extends JpaRepository<UserRoleEntity, Integer> {}
