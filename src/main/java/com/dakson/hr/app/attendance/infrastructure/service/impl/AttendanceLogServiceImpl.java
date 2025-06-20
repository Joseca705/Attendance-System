package com.dakson.hr.app.attendance.infrastructure.service.impl;

import com.dakson.hr.app.attendance.api.model.request.AttendaceRequestDto;
import com.dakson.hr.app.attendance.domain.constant.AttendaceStatus;
import com.dakson.hr.app.attendance.domain.dao.AttendanceLogByEmployeeDao;
import com.dakson.hr.app.attendance.domain.dao.EmployeeChecksDao;
import com.dakson.hr.app.attendance.domain.entity.AttendanceLog;
import com.dakson.hr.app.attendance.domain.repository.AttendaceLogRepository;
import com.dakson.hr.app.attendance.infrastructure.exception.AlreadyCheckedException;
import com.dakson.hr.app.attendance.infrastructure.exception.AttendanceLogNotFoundException;
import com.dakson.hr.app.attendance.infrastructure.service.IAttendaceLogService;
import com.dakson.hr.common.model.response.BaseResponseDto;
import com.dakson.hr.core.user.domain.entity.Employee;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AttendanceLogServiceImpl implements IAttendaceLogService {

  private final AttendaceLogRepository attendaceLogRepository;

  @Value("${application.attendance.check-in-time}")
  private LocalTime checkInTime;

  @Value("${application.attendance.check-out-time}")
  private LocalTime checkOutTime;

  @Override
  @Transactional
  public BaseResponseDto registerCheckIn(AttendaceRequestDto attendance) {
    LocalDate today = LocalDate.now();

    this.attendaceLogRepository.findFlatByEmployeeIdAndLogDate(
        attendance.employeeId(),
        today
      ).ifPresent(log -> {
        if (log.getCheckInTime() != null) throw new AlreadyCheckedException(
          "Already checked in today."
        );
      });

    Employee currentEmployee = new Employee(attendance.employeeId());

    LocalTime now = LocalTime.now();
    AttendanceLog attendanceLog = AttendanceLog.builder()
      .employee(currentEmployee)
      .logDate(today)
      .checkInTime(now)
      .build();

    LocalTime cutoff = LocalTime.of(8, 36);
    if (now.isBefore(cutoff)) {
      attendanceLog.setStatus(AttendaceStatus.Present);
    } else {
      attendanceLog.setStatus(AttendaceStatus.Late);
    }

    if (attendance.remarks() != null) {
      attendanceLog.setRemarks(attendance.remarks());
    }

    this.attendaceLogRepository.save(attendanceLog);

    return new BaseResponseDto("Log attendance created successfully.");
  }

  @Override
  @Transactional
  public BaseResponseDto registerCheckOut(AttendaceRequestDto attendance) {
    LocalDate today = LocalDate.now();

    EmployeeChecksDao employeeChecks =
      this.attendaceLogRepository.findFlatByEmployeeIdAndLogDate(
          attendance.employeeId(),
          today
        ).orElseThrow(AttendanceLogNotFoundException::new);

    if (
      employeeChecks.getCheckOutTime() != null
    ) throw new AlreadyCheckedException("Already checked out today.");

    LocalTime now = LocalTime.now();

    this.attendaceLogRepository.updateCheckOut(
        now,
        attendance.remarks(),
        employeeChecks.getId()
      );

    return new BaseResponseDto("Log attendance created successfully.");
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
