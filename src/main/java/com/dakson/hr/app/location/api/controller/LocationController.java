package com.dakson.hr.app.location.api.controller;

import com.dakson.hr.app.location.api.model.response.LocationResponseDto;
import com.dakson.hr.app.location.infrastructure.service.ILocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/location")
public class LocationController {

  private final ILocationService locationService;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<LocationResponseDto> getLocationById(
    @PathVariable Integer id
  ) {
    return ResponseEntity.ok(locationService.findById(id));
  }
}
