package com.weather.exception_handling;

import com.weather.model.dto.ErrorResponseDto;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvisor {

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<ErrorResponseDto> getErrorResponseEntity(
      NoSuchElementException ex) {
    return createErrorResponse(HttpStatus.NOT_FOUND, ex, ex.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponseDto> getErrorResponseEntity(
      IllegalArgumentException ex) {
    return createErrorResponse(HttpStatus.CONFLICT, ex, ex.getMessage());
  }

  private ResponseEntity<ErrorResponseDto> createErrorResponse(HttpStatus status, Exception ex,
      String message) {

    return new ResponseEntity<>(ErrorResponseDto.builder()
        .timestamp(LocalDateTime.now())
        .error(ex.getClass().getSimpleName())
        .status(status.value())
        .message(message)
        .build(),
        status);
  }
}
