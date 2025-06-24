package com.dakson.hr.app.attendance.api.external;

import com.dakson.hr.app.attendance.api.model.request.AttendaceRequestDto;
import com.dakson.hr.app.attendance.infrastructure.service.IAttendaceLogService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RabbitMqService {

  private final IAttendaceLogService attendaceLogService;
  private final Validator validator;

  @RabbitListener(queues = "${fingerprint.queue}")
  public void receiveFingerprintData(AttendaceRequestDto message) {
    try {
      var violations = validator.validate(message);
      if (!violations.isEmpty()) {
        throw new ConstraintViolationException(violations);
      }
      attendaceLogService.registerChecks(message);
    } catch (Exception e) {
      System.out.println(e.getLocalizedMessage());
    }
  }
}
