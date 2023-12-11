package com.weather.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weather.AbstractTest;
import com.weather.model.dto.WeatherDto;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;


@Sql(value = "/db/WeatherController-before-test.sql",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class WeatherControllerTest extends AbstractTest {

  private Gson gson;
  private WeatherDto weatherDto;
  private AutoCloseable closeable;

  @BeforeEach
  void setUp() {
    closeable = MockitoAnnotations.openMocks(this);
    gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    weatherDto = WeatherDto.builder()
        .date("1985-01-01")
        .city("Nashville")
        .state("Tennessee")
        .lon(BigDecimal.valueOf(36.1189))
        .lat(BigDecimal.valueOf(-86.6892))
        .temperatures(List.of(BigDecimal.valueOf(17.3), BigDecimal.valueOf(16.8)))
        .build();
  }

  @AfterEach
  void closeService() throws Exception {
    closeable.close();
  }

  @Test
  @WithMockUser(roles = {"EDITOR"})
  void createWeatherRecord() throws Exception {
    mockMvc.perform(post("/weather")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(weatherDto)))
        .andExpect(status().isCreated());
  }

  @Test
  @WithMockUser(value = "spring")
  void createWeatherRecordWithNotEditorRole() throws Exception {
    mockMvc.perform(post("/weather")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(weatherDto)))
        .andExpect(status().isForbidden());
  }


  @Test
  @WithMockUser(value = "spring")
  void getWeatherDataByParametersWithNoParameters() throws Exception {
    this.mockMvc.perform(get("/weather/"))
        .andExpect(status().isOk())
        .andExpect(content().json(gson.toJson(List.of(weatherDto))));
  }

  @Test
  @WithMockUser(value = "spring")
  void getWeatherDataByParametersWithIncorrectSortParameter() throws Exception {
    this.mockMvc.perform(get("/weather/")
            .param("sort", "sort"))
        .andExpect(status().isConflict());
  }

  @Test
  @WithMockUser(value = "spring")
  void getWeatherDataByParametersMatchAllParameters() throws Exception {
    this.mockMvc.perform(get("/weather/")
            .param("date", "1985-01-01")
            .param("city", "nashville"))
        .andExpect(status().isOk())
        .andExpect(content().json(gson.toJson(List.of(weatherDto))));
  }

  @Test
  @WithMockUser(value = "spring")
  void getWeatherDataByParametersNoMatchParameters() throws Exception {
    this.mockMvc.perform(get("/weather/")
            .param("date", "1900-01-01")
            .param("city", "city"))
        .andExpect(status().isOk())
        .andExpect(content().json(gson.toJson(List.of())));
  }

  @Test
  @WithMockUser(value = "spring")
  void findById() throws Exception {
    mockMvc.perform(get("/weather/{id}", 10))
        .andExpect(status().isOk())
        .andExpect(content().json(gson.toJson(weatherDto)));
  }

  @Test
  @WithMockUser(value = "spring")
  void findByIdReturnNotFound() throws Exception {
    this.mockMvc.perform(get("/weather/{id}", 100))
        .andExpect(status().isNotFound());
  }

}
