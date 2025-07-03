package com.dakson.hr.app.location.infrastructure.service.impl;

import com.dakson.hr.app.location.api.model.request.CreateDepartmentRequestDto;
import com.dakson.hr.app.location.api.model.request.UpdateDepartmentRequestDto;
import com.dakson.hr.app.location.api.model.response.DepartmentResponseDto;
import com.dakson.hr.app.location.api.model.response.DetailedDepartmentResponseDto;
import com.dakson.hr.app.location.domain.dao.DepartmentResponseDao;
import com.dakson.hr.app.location.domain.dao.DetailedDepartmentDao;
import com.dakson.hr.app.location.domain.entity.Department;
import com.dakson.hr.app.location.domain.entity.Location;
import com.dakson.hr.app.location.domain.repository.DepartmentRepository;
import com.dakson.hr.app.location.domain.repository.LocationRepository;
import com.dakson.hr.app.location.infrastructure.exception.AlreadyAssignedException;
import com.dakson.hr.app.location.infrastructure.exception.AlreadyAssignedLocationException;
import com.dakson.hr.app.location.infrastructure.exception.ManagerAlreadyAssignedToDepartmentException;
import com.dakson.hr.app.location.infrastructure.exception.UserNotFoundException;
import com.dakson.hr.app.location.infrastructure.service.DepartmentService;
import com.dakson.hr.common.constant.Status;
import com.dakson.hr.common.exception.ResourceNotFoundException;
import com.dakson.hr.common.model.response.BaseResponseDto;
import com.dakson.hr.common.util.CurrentUserJwtUtil;
import com.dakson.hr.core.user.domain.entity.Employee;
import com.dakson.hr.core.user.domain.repository.EmployeeRepository;
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
  private final EmployeeRepository employeeRepository;

  @Transactional
  @Override
  public DepartmentResponseDto create(CreateDepartmentRequestDto body) {
    // Validate location
    Location location = locationRepository
      .findById(body.getLocationId())
      .filter(l -> l.getStatus() == Status.ACTIVE)
      .orElseThrow(() ->
        new ResourceNotFoundException(
          "Location with id " +
          body.getLocationId() +
          " not found or not active."
        )
      );

    // Validate manager if provided
    Employee manager = null;
    if (body.getManagerId() != null) {
      manager = employeeRepository
        .findById(body.getManagerId())
        .filter(e -> e.getStatus() == Status.ACTIVE)
        .orElseThrow(() ->
          new UserNotFoundException(
            "Manager with id " +
            body.getManagerId() +
            " not found or not active."
          )
        );

      // Check if manager is already assigned to any department
      if (
        departmentRepository.isManagerAssignedToAnyDepartment(
          body.getManagerId()
        )
      ) {
        throw new ManagerAlreadyAssignedToDepartmentException(
          "Manager with id " +
          body.getManagerId() +
          " is already assigned to another department."
        );
      }
    }

    Department department = new Department(body.getName(), location, manager);

    Department saved = departmentRepository.save(department);
    return modelMapper.map(saved, DepartmentResponseDto.class);
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

  @Transactional
  @Override
  public BaseResponseDto updateById(
    UpdateDepartmentRequestDto body,
    Integer id
  ) {
    // Validate department exists and is active
    if (!departmentRepository.existsByIdAndStatus(id, Status.ACTIVE)) {
      throw new ResourceNotFoundException(
        "Department with id " + id + " not found or not active."
      );
    }

    Integer updatedBy = CurrentUserJwtUtil.getCurrentUserId();
    departmentRepository.updateDepartmentName(id, body.getName(), updatedBy);

    return BaseResponseDto.builder()
      .message("Department updated successfully")
      .build();
  }

  @Transactional
  @Override
  public BaseResponseDto deleteById(Integer id) {
    // Validate department exists and is active
    if (!departmentRepository.existsByIdAndStatus(id, Status.ACTIVE)) {
      throw new ResourceNotFoundException(
        "Department with id " + id + " not found or not active."
      );
    }

    Integer updatedBy = CurrentUserJwtUtil.getCurrentUserId();
    departmentRepository.deleteDepartmentById(id, updatedBy);

    return BaseResponseDto.builder()
      .message("Department deleted successfully")
      .build();
  }

  @Transactional(readOnly = true)
  @Override
  public Page<DepartmentResponseDto> getPaginatedDepartments(
    Pageable pageable
  ) {
    Page<DepartmentResponseDao> daoPage =
      departmentRepository.findAllByStatusCustom(Status.ACTIVE, pageable);
    return daoPage.map(dao -> modelMapper.map(dao, DepartmentResponseDto.class)
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
