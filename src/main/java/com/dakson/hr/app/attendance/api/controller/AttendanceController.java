package com.dakson.hr.app.attendance.api.controller;

import com.dakson.hr.app.attendance.api.model.request.AttendaceRequestDto;
import com.dakson.hr.app.attendance.infrastructure.service.IAttendaceLogService;
import com.dakson.hr.common.model.response.BaseResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

  private final IAttendaceLogService attendaceLogService;

  @PostMapping("/check-in")
  public ResponseEntity<BaseResponseDto> registerCheckIn(
    @Valid @RequestBody AttendaceRequestDto attendance
  ) {
    BaseResponseDto response = attendaceLogService.registerCheckIn(attendance);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/check-out")
  public ResponseEntity<BaseResponseDto> registerCheckOut(
    @Valid @RequestBody AttendaceRequestDto attendance
  ) {
    BaseResponseDto response = attendaceLogService.registerCheckOut(attendance);
    return ResponseEntity.ok(response);
  }
}
