package com.dakson.hr.core.user.domain.repository;

import com.dakson.hr.core.user.domain.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonEntityRepository
  extends JpaRepository<PersonEntity, Integer> {}
