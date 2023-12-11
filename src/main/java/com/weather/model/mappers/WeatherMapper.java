package com.weather.model.mappers;

import com.weather.model.dto.WeatherDto;
import com.weather.model.entity.Temperature;
import com.weather.model.entity.Weather;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface WeatherMapper {

  @Mapping(source = "weather.date", target = "date",
      qualifiedByName = "mapDate")
  @Mapping(source = "weather.temperatures", target = "temperatures",
      qualifiedByName = "mapTemperatures")
  WeatherDto toDto(Weather weather);

  @Named("mapDate")
  default String mapDate(LocalDate date) {
    return date.toString();
  }

  @Named("mapTemperatures")
  default List<BigDecimal> mapTemperatures(List<Temperature> temperatures) {
    return temperatures.stream()
        .map(Temperature::getValue)
        .collect(Collectors.toList());
  }
}
