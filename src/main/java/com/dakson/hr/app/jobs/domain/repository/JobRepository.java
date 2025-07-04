package com.dakson.hr.app.jobs.domain.repository;

import com.dakson.hr.app.jobs.domain.entity.Job;
import com.dakson.hr.common.constant.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
  Page<Job> findAllByStatus(Status status, Pageable pageable);
}
