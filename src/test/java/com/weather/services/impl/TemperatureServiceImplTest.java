package com.weather.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import com.weather.AbstractUnitTest;
import com.weather.model.entity.Temperature;
import com.weather.repository.TemperatureRepository;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class TemperatureServiceImplTest extends AbstractUnitTest {

  @Mock
  TemperatureRepository repository;

  @InjectMocks
  TemperatureServiceImpl service;

  @Test
  void save() {
    Long weatherDataId = 1l;
    when(repository.saveAll(anyList())).thenAnswer(i -> i.getArguments()[0]);
    List<BigDecimal> temperatures = List.of(BigDecimal.valueOf(17.3), BigDecimal.valueOf(16.8));
    List<Temperature> saved = service.save(temperatures, weatherDataId);

    assertEquals(saved.get(0).getValue(), temperatures.get(0));
    assertEquals(saved.get(1).getValue(), temperatures.get(1));
    assertEquals(saved.get(0).getWeather().getId(), weatherDataId);
    assertEquals(saved.get(1).getWeather().getId(), weatherDataId);

  }
}