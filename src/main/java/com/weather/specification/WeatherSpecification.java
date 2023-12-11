package com.weather.specification;

import com.weather.model.entity.Weather;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor
public class WeatherSpecification {

  public static Specification<Weather> getSpecification(LocalDate date, List<String> cities){
    return Specification.where(equalToDate(date)).and(equalToCities(cities));
  }

  private static Specification<Weather> equalToDate(LocalDate date){
    return (root, query, builder) -> date == null ? null : builder.equal(root.get("date"), date);
  }

  private static Specification<Weather> equalToCities(List<String> cities){
    return (root, query, builder) -> cities == null ? null : builder.lower(root.get("city"))
        .in(cities.stream().map(String::toLowerCase).collect(Collectors.toList()));
  }

}
