package com.dakson.hr.app.attendance.infrastructure.service.impl;

import com.dakson.hr.app.attendance.api.model.request.AttendaceRequestDto;
import com.dakson.hr.app.attendance.domain.constant.AttendaceStatus;
import com.dakson.hr.app.attendance.domain.dao.AttendanceLogByEmployeeDao;
import com.dakson.hr.app.attendance.domain.dao.EmployeeChecksDao;
import com.dakson.hr.app.attendance.domain.entity.AttendanceLog;
import com.dakson.hr.app.attendance.domain.repository.AttendaceLogRepository;
import com.dakson.hr.app.attendance.infrastructure.exception.AlreadyCheckedException;
import com.dakson.hr.app.attendance.infrastructure.service.AttendaceLogService;
import com.dakson.hr.common.model.response.BaseResponseDto;
import com.dakson.hr.core.user.domain.entity.Employee;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AttendanceLogServiceImpl implements AttendaceLogService {

  private final AttendaceLogRepository attendaceLogRepository;

  @Value("${application.attendance.check-in-time}")
  private LocalTime checkInTime;

  @Value("${application.attendance.check-out-time}")
  private LocalTime checkOutTime;

  @Transactional
  @Override
  public BaseResponseDto registerChecks(AttendaceRequestDto attendance) {
    LocalDateTime timestamp = attendance.timestamp();
    LocalDate today = timestamp.toLocalDate();
    LocalTime now = timestamp.toLocalTime();
    Long employeeId = attendance.employeeId();

    // Single query to get existing attendance record
    Optional<EmployeeChecksDao> existingRecord =
      attendaceLogRepository.findFlatByEmployeeIdAndLogDate(employeeId, today);

    if (existingRecord.isEmpty()) {
      // No record exists - perform check-in
      registerCheckIn(employeeId, today, now);
      return new BaseResponseDto("Checked in successfully");
    }
    EmployeeChecksDao recordEmployee = existingRecord.get();

    // Validate check-in time (must be at least 1 hour ago to allow check-out)
    if (recordEmployee.getCheckInTime().plusHours(1).isAfter(now)) {
      throw new AlreadyCheckedException(
        "Check-out is only allowed after 1 hour from check-in"
      );
    }
    // Check if already checked out
    if (recordEmployee.getCheckOutTime() != null) {
      throw new AlreadyCheckedException("Already checked-out today");
    }

    registerCheckOut(employeeId, now);

    return new BaseResponseDto("Checked out successfully");
  }

  private void registerCheckIn(
    Long employeeId,
    LocalDate today,
    LocalTime now
  ) {
    Employee currentEmployee = new Employee((int) (long) employeeId);
    // Determine status based on check-in time
    AttendaceStatus status = now.isBefore(checkInTime.plusMinutes(6))
      ? AttendaceStatus.Present
      : AttendaceStatus.Late;

    AttendanceLog attendanceLog = AttendanceLog.builder()
      .employee(currentEmployee)
      .logDate(today)
      .checkInTime(now)
      .status(status)
      .build();

    this.attendaceLogRepository.save(attendanceLog);
  }

  private void registerCheckOut(Long employeeId, LocalTime now) {
    this.attendaceLogRepository.updateCheckOut(now, employeeId);
  }

  @Override
  public Page<AttendanceLogByEmployeeDao> getAttendanceLogs(
    Integer employeeId,
    Pageable pageable
  ) {
    Employee employee = new Employee(employeeId);
    return this.attendaceLogRepository.findByEmployeeOrderByLogDateDesc(
        employee,
        pageable
      );
  }
}
