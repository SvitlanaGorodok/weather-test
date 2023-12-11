package com.weather.services;

import com.weather.model.dto.WeatherDto;
import java.time.LocalDate;
import java.util.List;

public interface WeatherService {

  WeatherDto save(WeatherDto weatherDto);

  WeatherDto findById(Long weatherId);

  List<WeatherDto> findAllByParameters(LocalDate date, List<String> cities, String sort);

}
