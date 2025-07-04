package com.dakson.hr.app.jobs.api.controller;

import com.dakson.hr.app.jobs.api.model.request.CreateUpdateJobHistoryRequestDto;
import com.dakson.hr.app.jobs.api.model.response.JobHistoryResponseDto;
import com.dakson.hr.app.jobs.infrastructure.service.JobHistoryService;
import com.dakson.hr.common.model.response.BaseResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
  name = "Job History Management",
  description = "APIs for managing employee job history records"
)
public class JobHistoryController {

  private final JobHistoryService jobHistoryService;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{jobHistoryId}")
  @Operation(
    summary = "Get job history by ID",
    description = "Retrieve a job history record by its unique identifier"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Job history found",
        content = @Content(
          schema = @Schema(implementation = JobHistoryResponseDto.class)
        )
      ),
      @ApiResponse(responseCode = "404", description = "Job history not found"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
    }
  )
  public ResponseEntity<JobHistoryResponseDto> readById(
    @PathVariable Integer jobHistoryId
  ) {
    JobHistoryResponseDto jobHistory = jobHistoryService.findById(jobHistoryId);
    return ResponseEntity.ok(jobHistory);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{jobHistoryId}")
  @Operation(
    summary = "Delete job history by ID",
    description = "Soft-delete a job history record by setting its status to INACTIVE"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Job history deleted",
        content = @Content(
          schema = @Schema(implementation = BaseResponseDto.class)
        )
      ),
      @ApiResponse(responseCode = "404", description = "Job history not found"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
    }
  )
  public ResponseEntity<BaseResponseDto> deleteById(
    @PathVariable Integer jobHistoryId
  ) {
    BaseResponseDto deleted = jobHistoryService.deleteById(jobHistoryId);
    return ResponseEntity.ok(deleted);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{jobHistoryId}")
  @Operation(
    summary = "Update job history by ID",
    description = "Update fields of a job history record by its unique identifier. Only provided fields will be updated."
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Job history updated",
        content = @Content(
          schema = @Schema(implementation = BaseResponseDto.class)
        )
      ),
      @ApiResponse(responseCode = "400", description = "Invalid input data"),
      @ApiResponse(responseCode = "404", description = "Job history not found"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
    }
  )
  public ResponseEntity<BaseResponseDto> updateById(
    @PathVariable Integer jobHistoryId,
    @RequestBody @Valid CreateUpdateJobHistoryRequestDto body
  ) {
    BaseResponseDto updated = jobHistoryService.updateById(body, jobHistoryId);
    return ResponseEntity.ok(updated);
  }
}
