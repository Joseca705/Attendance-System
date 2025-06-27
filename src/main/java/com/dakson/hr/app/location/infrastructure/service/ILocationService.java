package com.dakson.hr.app.location.infrastructure.service;

import com.dakson.hr.app.location.api.model.request.CreateLocationRequestDto;
import com.dakson.hr.app.location.api.model.request.UpdateLocationRequestDto;
import com.dakson.hr.app.location.api.model.response.LocationResponseDto;
import com.dakson.hr.common.model.response.BaseResponseDto;
import com.dakson.hr.common.service.CrudService;

public interface ILocationService
  extends
    CrudService<
      CreateLocationRequestDto,
      UpdateLocationRequestDto,
      LocationResponseDto,
      LocationResponseDto,
      BaseResponseDto,
      BaseResponseDto,
      Integer
    > {}
