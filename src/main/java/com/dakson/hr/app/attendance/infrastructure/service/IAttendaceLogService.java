package com.dakson.hr.app.attendance.infrastructure.service;

import com.dakson.hr.app.attendance.api.model.request.AttendaceRequestDto;
import com.dakson.hr.common.model.response.BaseResponseDto;

public interface IAttendaceLogService {
  BaseResponseDto registerCheckIn(AttendaceRequestDto attendance);

  BaseResponseDto registerCheckOut(AttendaceRequestDto attendance);
}
