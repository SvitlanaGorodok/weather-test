package com.weather.model.mappers;

import com.weather.model.dto.UserDto;
import com.weather.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserDto toDto(User user);

}
