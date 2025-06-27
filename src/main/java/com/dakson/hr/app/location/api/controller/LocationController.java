package com.dakson.hr.app.location.api.controller;

import com.dakson.hr.app.location.api.model.request.CreateLocationRequestDto;
import com.dakson.hr.app.location.api.model.request.UpdateLocationRequestDto;
import com.dakson.hr.app.location.api.model.response.LocationResponseDto;
import com.dakson.hr.app.location.infrastructure.service.LocationService;
import com.dakson.hr.common.model.response.BaseResponseDto;
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
public class LocationController {

  private final LocationService locationService;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping
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
  public ResponseEntity<LocationResponseDto> getLocationById(
    @PathVariable Integer id
  ) {
    LocationResponseDto location = locationService.findById(id);
    return ResponseEntity.ok(location);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
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
  public ResponseEntity<BaseResponseDto> updateLocation(
    @PathVariable Integer id,
    @RequestBody @Valid UpdateLocationRequestDto body
  ) {
    BaseResponseDto updatedLocation = locationService.updateById(body, id);
    return ResponseEntity.ok(updatedLocation);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<BaseResponseDto> deleteLocation(
    @PathVariable Integer id
  ) {
    BaseResponseDto deletedLocation = locationService.deleteById(id);
    return ResponseEntity.ok(deletedLocation);
  }
}
