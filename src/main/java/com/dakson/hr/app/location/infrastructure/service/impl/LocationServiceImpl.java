package com.dakson.hr.app.location.infrastructure.service.impl;

import com.dakson.hr.app.location.api.model.request.CreateLocationRequestDto;
import com.dakson.hr.app.location.api.model.request.UpdateLocationRequestDto;
import com.dakson.hr.app.location.api.model.response.LocationResponseDto;
import com.dakson.hr.app.location.domain.repository.LocationRepository;
import com.dakson.hr.app.location.infrastructure.service.ILocationService;
import com.dakson.hr.common.exception.ResourceNotFoundException;
import com.dakson.hr.common.model.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LocationServiceImpl implements ILocationService {

  private final LocationRepository locationRepository;
  private final ModelMapper modelMapper;

  @Transactional
  @Override
  public LocationResponseDto create(CreateLocationRequestDto body) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'create'");
  }

  @Transactional(readOnly = true)
  @Override
  public LocationResponseDto findById(Integer id) {
    return modelMapper.map(
      locationRepository
        .findById(id)
        .orElseThrow(() ->
          new ResourceNotFoundException(
            String.format("Location with id %s not found", id)
          )
        ),
      LocationResponseDto.class
    );
  }

  @Transactional
  @Override
  public LocationResponseDto updateById(
    UpdateLocationRequestDto body,
    Integer id
  ) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException(
      "Unimplemented method 'updateById'"
    );
  }

  @Transactional
  @Override
  public BaseResponseDto deleteById(Integer id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException(
      "Unimplemented method 'deleteById'"
    );
  }
}
