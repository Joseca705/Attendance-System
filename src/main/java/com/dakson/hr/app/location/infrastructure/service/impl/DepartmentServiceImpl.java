package com.dakson.hr.app.location.infrastructure.service.impl;

import com.dakson.hr.app.location.api.model.request.CreateDepartmentRequestDto;
import com.dakson.hr.app.location.api.model.response.DepartmentResponseDto;
import com.dakson.hr.app.location.api.model.response.DetailedDepartmentResponseDto;
import com.dakson.hr.app.location.domain.dao.DetailedDepartmentDao;
import com.dakson.hr.app.location.domain.repository.DepartmentRepository;
import com.dakson.hr.app.location.domain.repository.LocationRepository;
import com.dakson.hr.app.location.infrastructure.exception.AlreadyAssignedException;
import com.dakson.hr.app.location.infrastructure.exception.AlreadyAssignedLocationException;
import com.dakson.hr.app.location.infrastructure.exception.UserNotFoundException;
import com.dakson.hr.app.location.infrastructure.service.DepartmentService;
import com.dakson.hr.common.constant.Status;
import com.dakson.hr.common.exception.ResourceNotFoundException;
import com.dakson.hr.common.model.response.BaseResponseDto;
import com.dakson.hr.common.util.CurrentUserJwtUtil;
import com.dakson.hr.core.user.domain.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {

  private final DepartmentRepository departmentRepository;
  private final ModelMapper modelMapper;
  private final UserEntityRepository userEntityRepository;
  private final LocationRepository locationRepository;

  @Transactional
  @Override
  public DepartmentResponseDto create(CreateDepartmentRequestDto body) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'create'");
  }

  @Transactional(readOnly = true)
  @Override
  public DetailedDepartmentResponseDto findById(Integer id) {
    if (!departmentRepository.existsByIdAndStatus(id, Status.ACTIVE)) {
      throw new ResourceNotFoundException(
        String.format("Department with id %s not found.", id)
      );
    }
    DetailedDepartmentDao dao = departmentRepository
      .findDetailedById(id)
      .orElseThrow(() ->
        new ResourceNotFoundException(
          String.format("Department with id %s not found.", id)
        )
      );
    DetailedDepartmentResponseDto dto = modelMapper.map(
      dao,
      DetailedDepartmentResponseDto.class
    );
    // Manually map nested manager
    if (dao.getManagerId() != null) {
      dto.setManager(
        new DetailedDepartmentResponseDto.Employee(
          dao.getManagerId(),
          dao.getFirstName(),
          dao.getLastName()
        )
      );
    }
    // Manually map nested location
    if (dao.getLocationId() != null) {
      dto.setLocation(
        new DetailedDepartmentResponseDto.Location(
          dao.getLocationId(),
          dao.getLocationName()
        )
      );
    }
    return dto;
  }

  @Override
  public BaseResponseDto updateById(Object body, Integer id) {
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

  @Transactional(readOnly = true)
  @Override
  public Page<DepartmentResponseDto> getPaginatedDepartments(
    Pageable pageable
  ) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException(
      "Unimplemented method 'getPaginatedDepartments'"
    );
  }

  @Transactional
  @Override
  public BaseResponseDto assignManagerToDepartment(
    Integer departmentId,
    Integer managerId
  ) {
    Integer updatedBy = CurrentUserJwtUtil.getCurrentUserId();
    if (
      !departmentRepository.existsByIdAndStatus(departmentId, Status.ACTIVE)
    ) {
      throw new ResourceNotFoundException(
        "Department with id " + departmentId + " not found."
      );
    }
    if (
      updatedBy == null ||
      !userEntityRepository.existsByIdAndStatus(managerId, Status.ACTIVE)
    ) {
      throw new UserNotFoundException(
        "Manager with id " + managerId + " not found."
      );
    }
    if (
      departmentRepository.isManagerAlreadyAssigned(departmentId, managerId)
    ) {
      throw new AlreadyAssignedException(
        "Manager is already assigned to this department."
      );
    }
    departmentRepository.assignManagerToDepartment(
      departmentId,
      managerId,
      updatedBy
    );
    return BaseResponseDto.builder()
      .message("Manager assigned to department successfully")
      .build();
  }

  @Transactional
  @Override
  public BaseResponseDto changeDepartmentLocation(
    Integer departmentId,
    Integer locationId
  ) {
    Integer updatedBy = CurrentUserJwtUtil.getCurrentUserId();
    if (
      !departmentRepository.existsByIdAndStatus(departmentId, Status.ACTIVE)
    ) {
      throw new ResourceNotFoundException(
        "Department with id " + departmentId + " not found."
      );
    }
    if (!locationRepository.existsByIdAndStatus(locationId, Status.ACTIVE)) {
      throw new ResourceNotFoundException(
        "Location with id " + locationId + " not found."
      );
    }
    if (
      departmentRepository.isLocationAlreadyAssigned(departmentId, locationId)
    ) {
      throw new AlreadyAssignedLocationException(
        "Department is already assigned to this location."
      );
    }
    departmentRepository.changeDepartmentLocation(
      departmentId,
      locationId,
      updatedBy
    );
    return BaseResponseDto.builder()
      .message("Department location changed successfully")
      .build();
  }
}
