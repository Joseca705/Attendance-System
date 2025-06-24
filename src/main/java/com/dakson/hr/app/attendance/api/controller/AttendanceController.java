package com.dakson.hr.app.attendance.api.controller;

import com.dakson.hr.app.attendance.api.model.request.AttendaceRequestDto;
import com.dakson.hr.app.attendance.domain.dao.AttendanceLogByEmployeeDao;
import com.dakson.hr.app.attendance.infrastructure.service.IAttendaceLogService;
import com.dakson.hr.common.model.response.BaseResponseDto;
import com.dakson.hr.common.util.CurrentUserJwtUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

  private final IAttendaceLogService attendaceLogService;

  @PostMapping("/check-in-out")
  public ResponseEntity<BaseResponseDto> registerCheckInOut(
    @Valid @RequestBody AttendaceRequestDto attendance
  ) {
    BaseResponseDto response = attendaceLogService.registerChecks(attendance);
    return ResponseEntity.ok(response);
  }

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @GetMapping
  public ResponseEntity<Page<AttendanceLogByEmployeeDao>> getAttendanceLogs(
    @RequestParam(defaultValue = "0") @Min(0) Integer page,
    @RequestParam(defaultValue = "10") @Min(1) Integer size
  ) {
    Integer employeeId = CurrentUserJwtUtil.getCurrentUserId();
    Pageable pageable = PageRequest.of(page, size);
    return ResponseEntity.ok(
      attendaceLogService.getAttendanceLogs(employeeId, pageable)
    );
  }
}
