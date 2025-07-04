package com.dakson.hr.app.jobs.infrastructure.service.impl;

import com.dakson.hr.app.jobs.api.model.request.CreateUpdateJobHistoryRequestDto;
import com.dakson.hr.app.jobs.api.model.response.JobHistoryResponseDto;
import com.dakson.hr.app.jobs.domain.entity.Job;
import com.dakson.hr.app.jobs.domain.entity.JobHistory;
import com.dakson.hr.app.jobs.domain.repository.JobHistoryRepository;
import com.dakson.hr.app.jobs.domain.repository.JobRepository;
import com.dakson.hr.app.jobs.infrastructure.service.JobHistoryService;
import com.dakson.hr.app.location.domain.entity.Department;
import com.dakson.hr.app.location.domain.repository.DepartmentRepository;
import com.dakson.hr.common.constant.Status;
import com.dakson.hr.common.exception.ResourceNotFoundException;
import com.dakson.hr.common.model.response.BaseResponseDto;
import com.dakson.hr.core.user.domain.entity.Employee;
import com.dakson.hr.core.user.domain.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class JobHistoryServiceImpl implements JobHistoryService {

  private final JobHistoryRepository jobHistoryRepository;
  private final ModelMapper modelMapper;
  private final JobRepository jobRepository;
  private final EmployeeRepository employeeRepository;
  private final DepartmentRepository departmentRepository;

  @Transactional
  @Override
  public JobHistoryResponseDto create(CreateUpdateJobHistoryRequestDto body) {
    // Validate job
    Job job = jobRepository
      .findById(body.getJobId())
      .filter(j -> j.getStatus() == Status.ACTIVE)
      .orElseThrow(() ->
        new ResourceNotFoundException(
          "Job with id " + body.getJobId() + " not found or not active."
        )
      );

    // Validate department
    Department department = departmentRepository
      .findById(body.getDepartmentId())
      .filter(d -> d.getStatus() == Status.ACTIVE)
      .orElseThrow(() ->
        new ResourceNotFoundException(
          "Department with id " +
          body.getDepartmentId() +
          " not found or not active."
        )
      );

    // Validate employee
    Employee employee = employeeRepository
      .findById(body.getEmployeeId())
      .filter(e -> e.getStatus() == Status.ACTIVE)
      .orElseThrow(() ->
        new ResourceNotFoundException(
          "Employee with id " +
          body.getEmployeeId() +
          " not found or not active."
        )
      );

    JobHistory jobHistory = new JobHistory();
    jobHistory.setJob(job);
    jobHistory.setDepartment(department);
    jobHistory.setEmployee(employee);
    jobHistory.setStartDate(body.getStartDate());
    jobHistory.setEndDate(body.getEndDate());

    JobHistory saved = jobHistoryRepository.save(jobHistory);
    return modelMapper.map(saved, JobHistoryResponseDto.class);
  }

  @Transactional(readOnly = true)
  @Override
  public JobHistoryResponseDto findById(Integer id) {
    JobHistory jobHistory = jobHistoryRepository
      .findById(id)
      .filter(jh -> jh.getStatus() == Status.ACTIVE)
      .orElseThrow(() ->
        new ResourceNotFoundException(
          "JobHistory with id " + id + " not found or not active."
        )
      );
    return modelMapper.map(jobHistory, JobHistoryResponseDto.class);
  }

  @Transactional
  @Override
  public BaseResponseDto updateById(
    CreateUpdateJobHistoryRequestDto body,
    Integer id
  ) {
    JobHistory jobHistory = jobHistoryRepository
      .findById(id)
      .filter(jh -> jh.getStatus() == Status.ACTIVE)
      .orElseThrow(() ->
        new ResourceNotFoundException(
          "JobHistory with id " + id + " not found or not active."
        )
      );

    // Update only non-null fields
    if (body.getJobId() != null) {
      Job job = jobRepository
        .findById(body.getJobId())
        .filter(j -> j.getStatus() == Status.ACTIVE)
        .orElseThrow(() ->
          new ResourceNotFoundException(
            "Job with id " + body.getJobId() + " not found or not active."
          )
        );
      jobHistory.setJob(job);
    }
    if (body.getDepartmentId() != null) {
      Department department = departmentRepository
        .findById(body.getDepartmentId())
        .filter(d -> d.getStatus() == Status.ACTIVE)
        .orElseThrow(() ->
          new ResourceNotFoundException(
            "Department with id " +
            body.getDepartmentId() +
            " not found or not active."
          )
        );
      jobHistory.setDepartment(department);
    }
    if (body.getEmployeeId() != null) {
      Employee employee = employeeRepository
        .findById(body.getEmployeeId())
        .filter(e -> e.getStatus() == Status.ACTIVE)
        .orElseThrow(() ->
          new ResourceNotFoundException(
            "Employee with id " +
            body.getEmployeeId() +
            " not found or not active."
          )
        );
      jobHistory.setEmployee(employee);
    }
    if (body.getStartDate() != null) {
      jobHistory.setStartDate(body.getStartDate());
    }
    if (body.getEndDate() != null) {
      jobHistory.setEndDate(body.getEndDate());
    }

    jobHistoryRepository.save(jobHistory);
    return BaseResponseDto.builder()
      .message("JobHistory updated successfully")
      .build();
  }

  @Transactional
  @Override
  public BaseResponseDto deleteById(Integer id) {
    JobHistory jobHistory = jobHistoryRepository
      .findById(id)
      .filter(jh -> jh.getStatus() == Status.ACTIVE)
      .orElseThrow(() ->
        new ResourceNotFoundException(
          "JobHistory with id " + id + " not found or not active."
        )
      );
    jobHistory.setStatus(Status.INACTIVE);
    jobHistoryRepository.save(jobHistory);
    return BaseResponseDto.builder()
      .message("JobHistory deleted (set to INACTIVE) successfully")
      .build();
  }
}
