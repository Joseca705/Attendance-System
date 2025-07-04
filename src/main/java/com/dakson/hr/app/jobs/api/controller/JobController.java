package com.dakson.hr.app.jobs.api.controller;

import com.dakson.hr.app.jobs.api.model.request.CreateJobRequestDto;
import com.dakson.hr.app.jobs.api.model.request.UpdateJobRequestDto;
import com.dakson.hr.app.jobs.api.model.response.JobResponseDto;
import com.dakson.hr.app.jobs.infrastructure.service.JobService;
import com.dakson.hr.common.model.response.BaseResponseDto;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/job")
public class JobController {

  private final JobService jobService;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping
  public ResponseEntity<Page<JobResponseDto>> getPaginatedJobs(
    @PageableDefault(size = 10, sort = "id") Pageable pageable
  ) {
    Page<JobResponseDto> jobs = jobService.getPaginatedJobs(pageable);
    return ResponseEntity.ok(jobs);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{jobId}")
  public ResponseEntity<JobResponseDto> getJobById(
    @PathVariable Integer jobId
  ) {
    JobResponseDto job = jobService.findById(jobId);
    return ResponseEntity.ok(job);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<JobResponseDto> createJob(
    @RequestBody @Valid CreateJobRequestDto newJob
  ) {
    JobResponseDto job = jobService.create(newJob);
    return ResponseEntity.created(URI.create("/job/" + job.getId())).body(job);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{jobId}")
  public ResponseEntity<BaseResponseDto> updateJob(
    @PathVariable Integer jobId,
    @RequestBody @Valid UpdateJobRequestDto body
  ) {
    BaseResponseDto updatedJob = jobService.updateById(body, jobId);
    return ResponseEntity.ok(updatedJob);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{jobId}")
  public ResponseEntity<BaseResponseDto> deleteJob(
    @PathVariable Integer jobId
  ) {
    BaseResponseDto deletedJob = jobService.deleteById(jobId);
    return ResponseEntity.ok(deletedJob);
  }
}
