package com.dakson.hr.core.authentication.api.model.response;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseResponseDto implements Serializable {

  private String message;
}
