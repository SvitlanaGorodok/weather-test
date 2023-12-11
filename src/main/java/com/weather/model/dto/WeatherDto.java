package com.weather.model.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDto {
  private Long id;
  private String date;
  private BigDecimal lat;
  private BigDecimal lon;
  private String city;
  private String state;
  private List<BigDecimal> temperatures;
}
