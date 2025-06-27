package com.dakson.hr.app.location.infrastructure.service;

import com.dakson.hr.app.location.api.model.request.CreateLocationRequestDto;
import com.dakson.hr.app.location.api.model.request.UpdateLocationRequestDto;
import com.dakson.hr.app.location.api.model.response.LocationResponseDto;
import com.dakson.hr.common.model.response.BaseResponseDto;
import com.dakson.hr.common.service.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LocationService
  extends
    CrudService<
      CreateLocationRequestDto,
      UpdateLocationRequestDto,
      LocationResponseDto,
      LocationResponseDto,
      BaseResponseDto,
      BaseResponseDto,
      Integer
    > {
  Page<LocationResponseDto> getPaginatedLocations(Pageable pageable);
}
