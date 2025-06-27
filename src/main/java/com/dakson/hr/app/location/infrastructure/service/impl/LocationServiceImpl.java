package com.dakson.hr.app.location.infrastructure.service.impl;

import com.dakson.hr.app.location.api.model.request.CreateLocationRequestDto;
import com.dakson.hr.app.location.api.model.request.UpdateLocationRequestDto;
import com.dakson.hr.app.location.api.model.response.LocationResponseDto;
import com.dakson.hr.app.location.domain.entity.Location;
import com.dakson.hr.app.location.domain.repository.LocationRepository;
import com.dakson.hr.app.location.infrastructure.service.ILocationService;
import com.dakson.hr.common.exception.ResourceNotFoundException;
import com.dakson.hr.common.model.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class LocationServiceImpl implements ILocationService {

  private final LocationRepository locationRepository;
  private final ModelMapper modelMapper;

  @Transactional
  @Override
  public LocationResponseDto create(CreateLocationRequestDto body) {
    Location createdLocation =
      this.locationRepository.save(modelMapper.map(body, Location.class));
    return modelMapper.map(createdLocation, LocationResponseDto.class);
  }

  @Transactional(readOnly = true)
  @Override
  public LocationResponseDto findById(Integer id) {
    Location locationFound =
      this.locationRepository.findById(id).orElseThrow(() ->
          new ResourceNotFoundException(
            String.format("Location with id %s not found.", id)
          )
        );
    return modelMapper.map(locationFound, LocationResponseDto.class);
  }

  @Transactional
  @Override
  public BaseResponseDto updateById(UpdateLocationRequestDto body, Integer id) {
    Location locationFound =
      this.locationRepository.findById(id).orElseThrow(() ->
          new ResourceNotFoundException(
            String.format("Location with id %s not found.", id)
          )
        );
    if (body.getCity() != null) {
      locationFound.setCity(body.getCity());
    }
    if (body.getStreetAddress() != null) {
      locationFound.setStreetAddress(body.getStreetAddress());
    }

    this.locationRepository.save(locationFound);
    return BaseResponseDto.builder()
      .message("Location updated successfully")
      .build();
  }

  @Transactional
  @Override
  public BaseResponseDto deleteById(Integer id) {
    this.locationRepository.findById(id).orElseThrow(() ->
        new ResourceNotFoundException(
          String.format("Location with id %s not found.", id)
        )
      );
    this.locationRepository.deleteLocationRegister(id);
    return BaseResponseDto.builder()
      .message("Location deleted successfully")
      .build();
  }
}
