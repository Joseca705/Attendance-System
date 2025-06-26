package com.dakson.hr.app.jobs.domain.repository;

import com.dakson.hr.app.jobs.domain.entity.JobHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobHistoryRepository
  extends JpaRepository<JobHistory, Integer> {}
