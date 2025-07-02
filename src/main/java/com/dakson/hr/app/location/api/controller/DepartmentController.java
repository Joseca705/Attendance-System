package com.dakson.hr.app.location.api.controller;

import com.dakson.hr.app.location.api.model.request.CreateDepartmentRequestDto;
import com.dakson.hr.app.location.api.model.response.DepartmentResponseDto;
import com.dakson.hr.app.location.api.model.response.DetailedDepartmentResponseDto;
import com.dakson.hr.app.location.infrastructure.service.DepartmentService;
import com.dakson.hr.common.model.request.IdRequestDto;
import com.dakson.hr.common.model.response.BaseResponseDto;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/department")
public class DepartmentController {

  private final DepartmentService departmentService;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping
  public ResponseEntity<Page<DepartmentResponseDto>> getPaginatedDepartments(
    @PageableDefault(size = 10, sort = "id") Pageable pageable
  ) {
    Page<DepartmentResponseDto> departments =
      departmentService.getPaginatedDepartments(pageable);
    return ResponseEntity.ok(departments);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<DetailedDepartmentResponseDto> getDepartmentById(
    @PathVariable Integer id
  ) {
    DetailedDepartmentResponseDto department = departmentService.findById(id);
    return ResponseEntity.ok(department);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<DepartmentResponseDto> createDepartment(
    @RequestBody @Valid CreateDepartmentRequestDto newDepartment
  ) {
    DepartmentResponseDto department = departmentService.create(newDepartment);
    return ResponseEntity.created(
      URI.create("/department/" + department.getId())
    ).body(department);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PatchMapping("/{departmentId}/manager")
  public ResponseEntity<BaseResponseDto> assignManagerToDepartment(
    @PathVariable Integer departmentId,
    @RequestBody @Valid IdRequestDto managerId
  ) {
    BaseResponseDto response = departmentService.assignManagerToDepartment(
      departmentId,
      managerId.getId()
    );
    return ResponseEntity.ok(response);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PatchMapping("/{departmentId}/location")
  public ResponseEntity<BaseResponseDto> changeDepartmentLocation(
    @PathVariable Integer departmentId,
    @RequestBody @Valid IdRequestDto locationId
  ) {
    BaseResponseDto response = departmentService.changeDepartmentLocation(
      departmentId,
      locationId.getId()
    );
    return ResponseEntity.ok(response);
  }
}
