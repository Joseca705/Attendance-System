package com.dakson.hr.app.location.domain.repository;

import com.dakson.hr.app.location.domain.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository
  extends JpaRepository<Department, Integer> {}
