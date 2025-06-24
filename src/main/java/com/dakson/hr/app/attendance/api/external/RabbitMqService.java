package com.dakson.hr.app.attendance.api.external;

import com.dakson.hr.app.attendance.api.model.request.AttendaceRequestDto;
import com.dakson.hr.app.attendance.infrastructure.service.IAttendaceLogService;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class RabbitMqService {

  private final IAttendaceLogService attendaceLogService;
  private final Validator validator;

  @RabbitListener(queues = "${fingerprint.queue}")
  public void receiveFingerprintData(AttendaceRequestDto message) {
    try {
      var violations = validator.validate(message);
      if (!violations.isEmpty()) {
        log.error("Validation errors: {}", violations);
      }
      attendaceLogService.registerChecks(message);
    } catch (Exception e) {
      log.error("Error processing fingerprint data: {}", message, e);
    }
  }
}
