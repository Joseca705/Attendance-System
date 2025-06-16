package com.dakson.hr.core.authorization.domain.repository;

import com.dakson.hr.core.authorization.domain.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleEntityRepository
  extends JpaRepository<RoleEntity, Integer> {}
