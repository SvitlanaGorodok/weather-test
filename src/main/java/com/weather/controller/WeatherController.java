package com.weather.controller;

import com.weather.model.dto.WeatherDto;
import com.weather.services.WeatherService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
@Slf4j
public class WeatherController {

  private final WeatherService service;

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ROLE_EDITOR')")
  WeatherDto createWeatherRecord(@RequestBody @Validated WeatherDto weatherDto){
    return service.save(weatherDto);
  }

  @GetMapping("/")
  @ResponseStatus(HttpStatus.OK)
  List<WeatherDto> getWeatherDataByParameters(@RequestParam(required = false) LocalDate date,
      @RequestParam(required = false, name = "city") List<String> cities,
      @RequestParam(required = false) String sort){
    return service.findAllByParameters(date, cities, sort);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  WeatherDto findById(@PathVariable Long id){
    return service.findById(id);
  }
}
