package com.dakson.hr.app.jobs.infrastructure.service.impl;

import com.dakson.hr.app.jobs.api.model.request.CreateJobRequestDto;
import com.dakson.hr.app.jobs.api.model.request.UpdateJobRequestDto;
import com.dakson.hr.app.jobs.api.model.response.JobResponseDto;
import com.dakson.hr.app.jobs.domain.entity.Job;
import com.dakson.hr.app.jobs.domain.repository.JobRepository;
import com.dakson.hr.app.jobs.infrastructure.exception.InvalidSalaryRangeException;
import com.dakson.hr.app.jobs.infrastructure.service.JobService;
import com.dakson.hr.common.constant.Status;
import com.dakson.hr.common.exception.ResourceNotFoundException;
import com.dakson.hr.common.model.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class JobServiceImpl implements JobService {

  private final JobRepository jobRepository;
  private final ModelMapper modelMapper;

  @Transactional
  @Override
  public JobResponseDto create(CreateJobRequestDto body) {
    // Validate salary range
    if (body.getMinSalary().compareTo(body.getMaxSalary()) >= 0) {
      throw new InvalidSalaryRangeException(
        "Minimum salary must be less than maximum salary"
      );
    }

    Job job = new Job(body.getName(), body.getMinSalary(), body.getMaxSalary());

    Job saved = jobRepository.save(job);
    return modelMapper.map(saved, JobResponseDto.class);
  }

  @Transactional(readOnly = true)
  @Override
  public JobResponseDto findById(Integer id) {
    Job job = jobRepository
      .findById(id)
      .filter(j -> j.getStatus() == Status.ACTIVE)
      .orElseThrow(() ->
        new ResourceNotFoundException(
          "Job with id " + id + " not found or not active."
        )
      );

    return modelMapper.map(job, JobResponseDto.class);
  }

  @Transactional
  @Override
  public BaseResponseDto updateById(UpdateJobRequestDto body, Integer id) {
    // Validate job exists and is active
    Job job = jobRepository
      .findById(id)
      .filter(j -> j.getStatus() == Status.ACTIVE)
      .orElseThrow(() ->
        new ResourceNotFoundException(
          "Job with id " + id + " not found or not active."
        )
      );

    // Update fields if provided
    if (body.getName() != null) {
      job.setName(body.getName());
    }
    if (body.getMinSalary() != null) {
      job.setMinSalary(body.getMinSalary());
    }
    if (body.getMaxSalary() != null) {
      job.setMaxSalary(body.getMaxSalary());
    }

    // Validate salary range if both salaries are provided
    if (body.getMinSalary() != null && body.getMaxSalary() != null) {
      if (body.getMinSalary().compareTo(body.getMaxSalary()) >= 0) {
        throw new InvalidSalaryRangeException(
          "Minimum salary must be less than maximum salary"
        );
      }
    } else if (body.getMinSalary() != null && job.getMaxSalary() != null) {
      if (body.getMinSalary().compareTo(job.getMaxSalary()) >= 0) {
        throw new InvalidSalaryRangeException(
          "Minimum salary must be less than maximum salary"
        );
      }
    } else if (body.getMaxSalary() != null && job.getMinSalary() != null) {
      if (job.getMinSalary().compareTo(body.getMaxSalary()) >= 0) {
        throw new InvalidSalaryRangeException(
          "Minimum salary must be less than maximum salary"
        );
      }
    }

    jobRepository.save(job);
    return BaseResponseDto.builder()
      .message("Job updated successfully")
      .build();
  }

  @Transactional
  @Override
  public BaseResponseDto deleteById(Integer id) {
    // Validate job exists and is active
    Job job = jobRepository
      .findById(id)
      .filter(j -> j.getStatus() == Status.ACTIVE)
      .orElseThrow(() ->
        new ResourceNotFoundException(
          "Job with id " + id + " not found or not active."
        )
      );

    // Soft delete
    job.setStatus(Status.INACTIVE);

    jobRepository.save(job);
    return BaseResponseDto.builder()
      .message("Job deleted successfully")
      .build();
  }

  @Transactional(readOnly = true)
  @Override
  public Page<JobResponseDto> getPaginatedJobs(Pageable pageable) {
    Page<Job> jobs = jobRepository.findAllByStatus(Status.ACTIVE, pageable);
    return jobs.map(job -> modelMapper.map(job, JobResponseDto.class));
  }
}
