package com.weather.services;

import com.weather.model.entity.User;

public interface UserService {

  User findByLogin(String login);

}
