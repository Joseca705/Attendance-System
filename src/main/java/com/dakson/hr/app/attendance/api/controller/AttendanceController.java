package com.dakson.hr.app.attendance.api.controller;

import com.dakson.hr.app.attendance.api.model.request.AttendaceRequestDto;
import com.dakson.hr.app.attendance.domain.dao.AttendanceLogByEmployeeDao;
import com.dakson.hr.app.attendance.infrastructure.service.AttendaceLogService;
import com.dakson.hr.common.model.response.BaseResponseDto;
import com.dakson.hr.common.util.CurrentUserJwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/attendance")
@Tag(
  name = "Attendance Management",
  description = "APIs for managing employee attendance"
)
public class AttendanceController {

  private final AttendaceLogService attendaceLogService;

  @PostMapping("/check-in-out")
  @Operation(
    summary = "Register check-in or check-out",
    description = "Register employee check-in or check-out based on the provided timestamp"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Attendance registered successfully",
        content = @Content(
          schema = @Schema(implementation = BaseResponseDto.class)
        )
      ),
      @ApiResponse(responseCode = "400", description = "Invalid request data"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
    }
  )
  public ResponseEntity<BaseResponseDto> registerCheckInOut(
    @Valid @RequestBody AttendaceRequestDto attendance
  ) {
    BaseResponseDto response = attendaceLogService.registerChecks(attendance);
    return ResponseEntity.ok(response);
  }

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @GetMapping
  @Operation(
    summary = "Get attendance logs",
    description = "Retrieve paginated attendance logs for the current user"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Attendance logs retrieved successfully",
        content = @Content(
          schema = @Schema(implementation = AttendanceLogByEmployeeDao.class)
        )
      ),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
    }
  )
  @SecurityRequirement(name = "Bearer Authentication")
  public ResponseEntity<Page<AttendanceLogByEmployeeDao>> getAttendanceLogs(
    @PageableDefault(size = 10, sort = "id") Pageable pageable
  ) {
    Integer employeeId = CurrentUserJwtUtil.getCurrentUserId();
    return ResponseEntity.ok(
      attendaceLogService.getAttendanceLogs(employeeId, pageable)
    );
  }
}
