package com.dakson.hr.app.location.infrastructure.service.impl;

import com.dakson.hr.app.location.api.model.request.CreateDepartmentRequestDto;
import com.dakson.hr.app.location.api.model.response.DepartmentResponseDto;
import com.dakson.hr.app.location.api.model.response.DetailedDepartmentResponseDto;
import com.dakson.hr.app.location.domain.repository.DepartmentRepository;
import com.dakson.hr.app.location.infrastructure.service.DepartmentService;
import com.dakson.hr.common.model.response.BaseResponseDto;
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

  @Transactional
  @Override
  public DepartmentResponseDto create(CreateDepartmentRequestDto body) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'create'");
  }

  @Transactional(readOnly = true)
  @Override
  public DetailedDepartmentResponseDto findById(Integer id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
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
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException(
      "Unimplemented method 'assignManagerToDepartment'"
    );
  }

  @Transactional
  @Override
  public BaseResponseDto changeDepartmentLocation(
    Integer departmentId,
    Integer locationId
  ) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException(
      "Unimplemented method 'changeDepartmentLocation'"
    );
  }
}
