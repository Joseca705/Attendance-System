package com.dakson.hr.app.jobs.infrastructure.service;

import com.dakson.hr.app.jobs.api.model.request.CreateJobRequestDto;
import com.dakson.hr.app.jobs.api.model.request.UpdateJobRequestDto;
import com.dakson.hr.app.jobs.api.model.response.JobResponseDto;
import com.dakson.hr.common.model.response.BaseResponseDto;
import com.dakson.hr.common.service.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobService
  extends
    CrudService<
      CreateJobRequestDto,
      UpdateJobRequestDto,
      JobResponseDto,
      JobResponseDto,
      BaseResponseDto,
      BaseResponseDto,
      Integer
    > {
  Page<JobResponseDto> getPaginatedJobs(Pageable pageable);
}
