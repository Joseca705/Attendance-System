package com.dakson.hr.app.jobs.api.controller;

import com.dakson.hr.app.jobs.api.model.request.CreateJobRequestDto;
import com.dakson.hr.app.jobs.api.model.request.UpdateJobRequestDto;
import com.dakson.hr.app.jobs.api.model.response.JobResponseDto;
import com.dakson.hr.app.jobs.infrastructure.service.JobService;
import com.dakson.hr.common.model.response.BaseResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Job Management", description = "APIs for managing jobs")
public class JobController {

  private final JobService jobService;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping
  @Operation(
    summary = "Get paginated jobs",
    description = "Retrieve a paginated list of jobs"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "List of jobs",
        content = @Content(
          schema = @Schema(implementation = JobResponseDto.class)
        )
      ),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
    }
  )
  public ResponseEntity<Page<JobResponseDto>> getPaginatedJobs(
    @PageableDefault(size = 10, sort = "id") Pageable pageable
  ) {
    Page<JobResponseDto> jobs = jobService.getPaginatedJobs(pageable);
    return ResponseEntity.ok(jobs);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{jobId}")
  @Operation(
    summary = "Get job by ID",
    description = "Retrieve a job by its unique identifier"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Job found",
        content = @Content(
          schema = @Schema(implementation = JobResponseDto.class)
        )
      ),
      @ApiResponse(responseCode = "404", description = "Job not found"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
    }
  )
  public ResponseEntity<JobResponseDto> getJobById(
    @PathVariable Integer jobId
  ) {
    JobResponseDto job = jobService.findById(jobId);
    return ResponseEntity.ok(job);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  @Operation(
    summary = "Create a new job",
    description = "Create a new job record"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "201",
        description = "Job created",
        content = @Content(
          schema = @Schema(implementation = JobResponseDto.class)
        )
      ),
      @ApiResponse(responseCode = "400", description = "Invalid job data"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
    }
  )
  public ResponseEntity<JobResponseDto> createJob(
    @RequestBody @Valid CreateJobRequestDto newJob
  ) {
    JobResponseDto job = jobService.create(newJob);
    return ResponseEntity.created(URI.create("/job/" + job.getId())).body(job);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{jobId}")
  @Operation(
    summary = "Update job by ID",
    description = "Update the details of a job by its unique identifier"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Job updated",
        content = @Content(
          schema = @Schema(implementation = BaseResponseDto.class)
        )
      ),
      @ApiResponse(responseCode = "400", description = "Invalid job data"),
      @ApiResponse(responseCode = "404", description = "Job not found"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
    }
  )
  public ResponseEntity<BaseResponseDto> updateJob(
    @PathVariable Integer jobId,
    @RequestBody @Valid UpdateJobRequestDto body
  ) {
    BaseResponseDto updatedJob = jobService.updateById(body, jobId);
    return ResponseEntity.ok(updatedJob);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{jobId}")
  @Operation(
    summary = "Delete job by ID",
    description = "Delete a job by its unique identifier"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Job deleted",
        content = @Content(
          schema = @Schema(implementation = BaseResponseDto.class)
        )
      ),
      @ApiResponse(responseCode = "404", description = "Job not found"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
    }
  )
  public ResponseEntity<BaseResponseDto> deleteJob(
    @PathVariable Integer jobId
  ) {
    BaseResponseDto deletedJob = jobService.deleteById(jobId);
    return ResponseEntity.ok(deletedJob);
  }
}
