package com.dakson.hr.app.location.api.controller;

import com.dakson.hr.app.location.api.model.request.CreateLocationRequestDto;
import com.dakson.hr.app.location.api.model.request.UpdateLocationRequestDto;
import com.dakson.hr.app.location.api.model.response.LocationResponseDto;
import com.dakson.hr.app.location.infrastructure.service.LocationService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/location")
@Tag(name = "Location Management", description = "APIs for managing locations")
public class LocationController {

  private final LocationService locationService;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping
  @Operation(
    summary = "Get paginated locations",
    description = "Retrieve a paginated list of locations"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "List of locations",
        content = @Content(
          schema = @Schema(implementation = LocationResponseDto.class)
        )
      ),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
    }
  )
  public ResponseEntity<Page<LocationResponseDto>> getPaginatedLocations(
    @PageableDefault(size = 10, sort = "id") Pageable pageable
  ) {
    Page<LocationResponseDto> locations = locationService.getPaginatedLocations(
      pageable
    );
    return ResponseEntity.ok(locations);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{id}")
  @Operation(
    summary = "Get location by ID",
    description = "Retrieve a location by its unique identifier"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Location found",
        content = @Content(
          schema = @Schema(implementation = LocationResponseDto.class)
        )
      ),
      @ApiResponse(responseCode = "404", description = "Location not found"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
    }
  )
  public ResponseEntity<LocationResponseDto> getLocationById(
    @PathVariable Integer id
  ) {
    LocationResponseDto location = locationService.findById(id);
    return ResponseEntity.ok(location);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  @Operation(
    summary = "Create a new location",
    description = "Create a new location record"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "201",
        description = "Location created",
        content = @Content(
          schema = @Schema(implementation = BaseResponseDto.class)
        )
      ),
      @ApiResponse(responseCode = "400", description = "Invalid location data"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
    }
  )
  public ResponseEntity<BaseResponseDto> createLocation(
    @RequestBody @Valid CreateLocationRequestDto newLocation
  ) {
    LocationResponseDto createdLocation = locationService.create(newLocation);
    return ResponseEntity.created(
      URI.create("/location/" + createdLocation.getId())
    ).body(
      BaseResponseDto.builder().message("Location created successfully").build()
    );
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  @Operation(
    summary = "Update location by ID",
    description = "Update the details of a location by its unique identifier"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Location updated",
        content = @Content(
          schema = @Schema(implementation = BaseResponseDto.class)
        )
      ),
      @ApiResponse(responseCode = "400", description = "Invalid location data"),
      @ApiResponse(responseCode = "404", description = "Location not found"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
    }
  )
  public ResponseEntity<BaseResponseDto> updateLocation(
    @PathVariable Integer id,
    @RequestBody @Valid UpdateLocationRequestDto body
  ) {
    BaseResponseDto updatedLocation = locationService.updateById(body, id);
    return ResponseEntity.ok(updatedLocation);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  @Operation(
    summary = "Delete location by ID",
    description = "Delete a location by its unique identifier"
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        responseCode = "200",
        description = "Location deleted",
        content = @Content(
          schema = @Schema(implementation = BaseResponseDto.class)
        )
      ),
      @ApiResponse(responseCode = "404", description = "Location not found"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
    }
  )
  public ResponseEntity<BaseResponseDto> deleteLocation(
    @PathVariable Integer id
  ) {
    BaseResponseDto deletedLocation = locationService.deleteById(id);
    return ResponseEntity.ok(deletedLocation);
  }
}
