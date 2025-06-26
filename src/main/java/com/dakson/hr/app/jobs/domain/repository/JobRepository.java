package com.dakson.hr.app.jobs.domain.repository;

import com.dakson.hr.app.jobs.domain.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {}
