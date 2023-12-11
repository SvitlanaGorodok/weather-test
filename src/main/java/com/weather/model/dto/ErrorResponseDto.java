package com.weather.model.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponseDto {


  private String error;
  private LocalDateTime timestamp;
  private Integer status;
  private String message;

  public ErrorResponseDto(Integer status, String message) {
    this.status = status;
    this.message = message;
  }
}
