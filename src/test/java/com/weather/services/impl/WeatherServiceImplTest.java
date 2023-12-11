package com.weather.services.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.weather.AbstractUnitTest;
import com.weather.model.dto.WeatherDto;
import com.weather.model.entity.Temperature;
import com.weather.model.entity.Weather;
import com.weather.model.mappers.WeatherMapper;
import com.weather.repository.WeatherRepository;
import com.weather.services.TemperatureService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

class WeatherServiceImplTest extends AbstractUnitTest {

  @Mock
  WeatherRepository weatherRepository;

  @Mock
  TemperatureService temperatureService;

  @Mock
  WeatherMapper mapper;

  @InjectMocks
  WeatherServiceImpl service;

  private WeatherDto weatherDto;

  private Weather weather;

  @BeforeEach
  void init() {
    weatherDto = WeatherDto.builder()
        .date("1985-01-01")
        .city("Nashville")
        .state("Tennessee")
        .lon(BigDecimal.valueOf(36.1189))
        .lat(BigDecimal.valueOf(-86.6892))
        .temperatures(List.of(BigDecimal.valueOf(17.3), BigDecimal.valueOf(16.8)))
        .build();

    List<Temperature> temperatures = List.of(
        Temperature.builder().value(BigDecimal.valueOf(17.3)).build(),
        Temperature.builder().value(BigDecimal.valueOf(16.8)).build());
    weather = Weather.builder()
        .id(1L)
        .date(LocalDate.parse("1985-01-01"))
        .city("City")
        .state("State")
        .lon(BigDecimal.valueOf(36.1189))
        .lat(BigDecimal.valueOf(-86.6892))
        .temperatures(temperatures)
        .build();
  }

  @Test
  void save() {
    when(weatherRepository.save(any(Weather.class))).thenReturn(weather);
    when(temperatureService.save(anyList(), anyLong())).thenAnswer(i -> i.getArguments()[0]);
    when(mapper.toDto(any(Weather.class))).thenReturn(weatherDto);

    service.save(weatherDto);

    verify(weatherRepository, times(1)).save(any(Weather.class));
    verify(temperatureService, times(1)).save(anyList(), anyLong());
    verify(mapper, times(1)).toDto(any(Weather.class));

  }

  @Test
  void findById() {
    when(weatherRepository.findById(anyLong())).thenReturn(Optional.of(weather));
    when(mapper.toDto(any(Weather.class))).thenReturn(weatherDto);

    service.findById(1L);

    verify(weatherRepository, times(1)).findById(anyLong());
    verify(mapper, times(1)).toDto(any(Weather.class));
  }

  @Test
  void findAllByParameters() {
    when(weatherRepository.findAll(any(Specification.class), any(Sort.class)))
        .thenReturn(List.of(weather));
    service.findAllByParameters(LocalDate.parse("1985-01-01"), List.of("city"), "date");
    verify(weatherRepository, times(1))
        .findAll(any(Specification.class), any(Sort.class));
  }
}