package com.dakson.hr.app.jobs.api.controller;

import com.dakson.hr.app.jobs.api.model.request.CreateUpdateJobHistoryRequestDto;
import com.dakson.hr.app.jobs.api.model.response.JobHistoryResponseDto;
import com.dakson.hr.app.jobs.infrastructure.service.JobHistoryService;
import com.dakson.hr.common.model.response.BaseResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/job-history")
public class JobHistoryController {

  private final JobHistoryService jobHistoryService;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{jobHistoryId}")
  public ResponseEntity<JobHistoryResponseDto> readById(
    @PathVariable Integer jobHistoryId
  ) {
    JobHistoryResponseDto jobHistory = jobHistoryService.findById(jobHistoryId);
    return ResponseEntity.ok(jobHistory);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{jobHistoryId}")
  public ResponseEntity<BaseResponseDto> deleteById(
    @PathVariable Integer jobHistoryId
  ) {
    BaseResponseDto deleted = jobHistoryService.deleteById(jobHistoryId);
    return ResponseEntity.ok(deleted);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{jobHistoryId}")
  public ResponseEntity<BaseResponseDto> updateById(
    @PathVariable Integer jobHistoryId,
    @RequestBody @Valid CreateUpdateJobHistoryRequestDto body
  ) {
    BaseResponseDto updated = jobHistoryService.updateById(body, jobHistoryId);
    return ResponseEntity.ok(updated);
  }
}
