package com.dakson.hr.app.location.infrastructure.service;

import com.dakson.hr.app.location.api.model.request.CreateDepartmentRequestDto;
import com.dakson.hr.app.location.api.model.request.UpdateDepartmentRequestDto;
import com.dakson.hr.app.location.api.model.response.DepartmentResponseDto;
import com.dakson.hr.app.location.api.model.response.DetailedDepartmentResponseDto;
import com.dakson.hr.common.model.response.BaseResponseDto;
import com.dakson.hr.common.service.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DepartmentService
  extends
    CrudService<
      CreateDepartmentRequestDto,
      UpdateDepartmentRequestDto,
      DepartmentResponseDto,
      DetailedDepartmentResponseDto,
      BaseResponseDto,
      BaseResponseDto,
      Integer
    > {
  Page<DepartmentResponseDto> getPaginatedDepartments(Pageable pageable);

  BaseResponseDto assignManagerToDepartment(
    Integer departmentId,
    Integer managerId
  );

  BaseResponseDto changeDepartmentLocation(
    Integer departmentId,
    Integer locationId
  );
}
