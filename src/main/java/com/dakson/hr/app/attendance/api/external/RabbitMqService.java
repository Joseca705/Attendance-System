package com.dakson.hr.app.attendance.api.external;

import com.dakson.hr.app.attendance.api.model.request.AttendaceRequestDto;
import com.dakson.hr.app.attendance.infrastructure.service.IAttendaceLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RabbitMqService {

  private final IAttendaceLogService attendaceLogService;

  @RabbitListener(queues = "${fingerprint.queue}")
  public void receiveFingerprintData(AttendaceRequestDto message) {
    attendaceLogService.registerChecks(message);
  }
}
