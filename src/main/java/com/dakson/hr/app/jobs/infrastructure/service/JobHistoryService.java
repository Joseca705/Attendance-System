package com.dakson.hr.app.jobs.infrastructure.service;

import com.dakson.hr.app.jobs.api.model.request.CreateUpdateJobHistoryRequestDto;
import com.dakson.hr.app.jobs.api.model.response.JobHistoryResponseDto;
import com.dakson.hr.common.model.response.BaseResponseDto;
import com.dakson.hr.common.service.CrudService;

public interface JobHistoryService
  extends
    CrudService<
      CreateUpdateJobHistoryRequestDto,
      CreateUpdateJobHistoryRequestDto,
      JobHistoryResponseDto,
      JobHistoryResponseDto,
      BaseResponseDto,
      BaseResponseDto,
      Integer
    > {}
