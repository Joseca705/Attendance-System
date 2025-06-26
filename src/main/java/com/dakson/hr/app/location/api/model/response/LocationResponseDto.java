package com.dakson.hr.app.location.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponseDto {

  Integer id;

  String city;

  String streetAddress;
}
