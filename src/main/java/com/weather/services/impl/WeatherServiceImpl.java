package com.weather.services.impl;

import com.weather.model.dto.WeatherDto;
import com.weather.model.entity.Temperature;
import com.weather.model.entity.Weather;
import com.weather.model.mappers.WeatherMapper;
import com.weather.repository.WeatherRepository;
import com.weather.services.TemperatureService;
import com.weather.services.WeatherService;
import com.weather.specification.WeatherSpecification;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherServiceImpl implements WeatherService {

  private final WeatherRepository weatherRepository;
  private final TemperatureService temperatureService;
  private final WeatherMapper mapper;

  @Override
  @Transactional
  public WeatherDto save(WeatherDto weatherDto) {
    Weather weather = Weather.builder()
        .date(LocalDate.parse(weatherDto.getDate()))
        .lat(weatherDto.getLat())
        .lon(weatherDto.getLon())
        .city(weatherDto.getCity())
        .state(weatherDto.getState())
        .build();

    weather = weatherRepository.save(weather);
    List<Temperature> savedTemperatures = temperatureService.save(weatherDto.getTemperatures(),
        weather.getId());
    weather.setTemperatures(savedTemperatures);

    log.info("Weather data were saved with id {}", weather.getId());

    return mapper.toDto(weather);
  }

  @Override
  public WeatherDto findById(Long weatherId) {
    Weather weather = weatherRepository.findById(weatherId)
        .orElseThrow(() -> {
          log.warn("Weather data for id {} were not found", weatherId);
          return new NoSuchElementException("Weather data for id " + weatherId + "were not found");
        });
    log.info("Weather data for id {} were found", weatherId);
    return mapper.toDto(weather);
  }

  @Override
  public List<WeatherDto> findAllByParameters(LocalDate date, List<String> cities, String sort) {

    Specification<Weather> spec = WeatherSpecification.getSpecification(date, cities);

    Order orderForId = Order.asc("id");
    Sort sortType = Sort.by(orderForId);

    if(sort != null){
      Order orderForDate = new Order(getDateSortDirection(sort), "date");
      sortType = Sort.by(orderForDate, orderForId);
    }

    List<WeatherDto> weatherData = weatherRepository.findAll(spec, sortType).stream()
        .map(mapper::toDto)
        .collect(Collectors.toList());

    log.info("Weather data for parameters: date {}, city {} were provided.", date, cities);

    return weatherData;
  }

  private Direction getDateSortDirection(String sort) {
    if (sort.equals("date")) {
      return Direction.ASC;
    } else if (sort.equals("-date")) {
      return Direction.DESC;
    } else {
      throw new IllegalArgumentException("Illegal parameter for sorting.");
    }
  }

}
