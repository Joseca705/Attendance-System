package com.dakson.hr.common.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Base response DTO for all API responses")
public class BaseResponseDto implements Serializable {

  @Schema(
    description = "Response message",
    example = "Operation completed successfully"
  )
  private String message;
}
