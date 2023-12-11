package com.weather.services.impl;

import com.weather.model.entity.Temperature;
import com.weather.model.entity.Weather;
import com.weather.repository.TemperatureRepository;
import com.weather.services.TemperatureService;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemperatureServiceImpl implements TemperatureService {

  private final TemperatureRepository repository;

  @Override
  public List<Temperature> save(List<BigDecimal> temperatures, Long weatherDataId) {
    List<Temperature> temperaturesToSave = temperatures.stream()
        .map(t -> Temperature.builder()
            .value(t)
            .weather(Weather.builder().id(weatherDataId).build())
            .build())
        .collect(Collectors.toList());
    return repository.saveAll(temperaturesToSave);
  }

}
