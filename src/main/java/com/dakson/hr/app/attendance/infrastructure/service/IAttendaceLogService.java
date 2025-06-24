package com.dakson.hr.app.attendance.infrastructure.service;

import com.dakson.hr.app.attendance.api.model.request.AttendaceRequestDto;
import com.dakson.hr.app.attendance.domain.dao.AttendanceLogByEmployeeDao;
import com.dakson.hr.common.model.response.BaseResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAttendaceLogService {
  BaseResponseDto registerChecks(AttendaceRequestDto attendance);

  Page<AttendanceLogByEmployeeDao> getAttendanceLogs(
    Integer employeeId,
    Pageable pageable
  );
}
