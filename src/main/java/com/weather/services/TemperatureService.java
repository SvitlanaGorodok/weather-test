package com.weather.services;

import com.weather.model.entity.Temperature;
import java.math.BigDecimal;
import java.util.List;

public interface TemperatureService {
    List<Temperature> save(List<BigDecimal> temperatures, Long weatherDataId);
}
