package com.dakson.hr.app.location.api.controller;

import com.dakson.hr.app.location.api.model.request.CreateDepartmentRequestDto;
import com.dakson.hr.app.location.api.model.request.UpdateDepartmentRequestDto;
import com.dakson.hr.app.location.api.model.response.DepartmentResponseDto;
import com.dakson.hr.app.location.api.model.response.DetailedDepartmentResponseDto;
import com.dakson.hr.app.location.infrastructure.service.DepartmentService;
import com.dakson.hr.common.model.request.IdRequestDto;
import com.dakson.hr.common.model.response.BaseResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/department")
@Tag(
  name = "Department Management",
  description = "APIs for managing departments"
)
public class DepartmentController {

  private final DepartmentService departmentService;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping
  @Operation(
    summary = "Get paginated departments",
    description = "Retrieve a paginated list of departments"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "List of departments",
        content = @Content(
          schema = @Schema(implementation = DepartmentResponseDto.class)
        )
      ),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
    }
  )
  public ResponseEntity<Page<DepartmentResponseDto>> getPaginatedDepartments(
    @PageableDefault(size = 10, sort = "id") Pageable pageable
  ) {
    Page<DepartmentResponseDto> departments =
      departmentService.getPaginatedDepartments(pageable);
    return ResponseEntity.ok(departments);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{id}")
  @Operation(
    summary = "Get department by ID",
    description = "Retrieve a department by its unique identifier"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Department found",
        content = @Content(
          schema = @Schema(implementation = DetailedDepartmentResponseDto.class)
        )
      ),
      @ApiResponse(responseCode = "404", description = "Department not found"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
    }
  )
  public ResponseEntity<DetailedDepartmentResponseDto> getDepartmentById(
    @PathVariable Integer id
  ) {
    DetailedDepartmentResponseDto department = departmentService.findById(id);
    return ResponseEntity.ok(department);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  @Operation(
    summary = "Create a new department",
    description = "Create a new department record"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "201",
        description = "Department created",
        content = @Content(
          schema = @Schema(implementation = DepartmentResponseDto.class)
        )
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Invalid department data"
      ),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
    }
  )
  public ResponseEntity<DepartmentResponseDto> createDepartment(
    @RequestBody @Valid CreateDepartmentRequestDto newDepartment
  ) {
    DepartmentResponseDto department = departmentService.create(newDepartment);
    return ResponseEntity.created(
      URI.create("/department/" + department.getId())
    ).body(department);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PatchMapping("/{departmentId}")
  @Operation(
    summary = "Update department by ID",
    description = "Update the details of a department by its unique identifier"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Department updated",
        content = @Content(
          schema = @Schema(implementation = BaseResponseDto.class)
        )
      ),
      @ApiResponse(
        responseCode = "400",
        description = "Invalid department data"
      ),
      @ApiResponse(responseCode = "404", description = "Department not found"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
    }
  )
  public ResponseEntity<BaseResponseDto> updateDepartment(
    @PathVariable Integer departmentId,
    @RequestBody @Valid UpdateDepartmentRequestDto updateDepartment
  ) {
    BaseResponseDto response = departmentService.updateById(
      updateDepartment,
      departmentId
    );
    return ResponseEntity.ok(response);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PatchMapping("/{departmentId}/manager")
  @Operation(
    summary = "Assign manager to department",
    description = "Assign a manager to a department by department and manager IDs"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Manager assigned",
        content = @Content(
          schema = @Schema(implementation = BaseResponseDto.class)
        )
      ),
      @ApiResponse(responseCode = "400", description = "Invalid manager ID"),
      @ApiResponse(
        responseCode = "404",
        description = "Department or manager not found"
      ),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
    }
  )
  public ResponseEntity<BaseResponseDto> assignManagerToDepartment(
    @PathVariable Integer departmentId,
    @RequestBody @Valid IdRequestDto managerId
  ) {
    BaseResponseDto response = departmentService.assignManagerToDepartment(
      departmentId,
      managerId.getId()
    );
    return ResponseEntity.ok(response);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PatchMapping("/{departmentId}/location")
  @Operation(
    summary = "Change department location",
    description = "Change the location of a department by department and location IDs"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Location changed",
        content = @Content(
          schema = @Schema(implementation = BaseResponseDto.class)
        )
      ),
      @ApiResponse(responseCode = "400", description = "Invalid location ID"),
      @ApiResponse(
        responseCode = "404",
        description = "Department or location not found"
      ),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
    }
  )
  public ResponseEntity<BaseResponseDto> changeDepartmentLocation(
    @PathVariable Integer departmentId,
    @RequestBody @Valid IdRequestDto locationId
  ) {
    BaseResponseDto response = departmentService.changeDepartmentLocation(
      departmentId,
      locationId.getId()
    );
    return ResponseEntity.ok(response);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{departmentId}")
  @Operation(
    summary = "Delete department by ID",
    description = "Delete a department by its unique identifier"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Department deleted",
        content = @Content(
          schema = @Schema(implementation = BaseResponseDto.class)
        )
      ),
      @ApiResponse(responseCode = "404", description = "Department not found"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
    }
  )
  public ResponseEntity<BaseResponseDto> deleteDepartmentById(
    @PathVariable Integer departmentId
  ) {
    BaseResponseDto response = departmentService.deleteById(departmentId);
    return ResponseEntity.ok(response);
  }
}
