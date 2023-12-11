package com.weather.services.impl;

import com.weather.model.entity.User;
import com.weather.repository.UserRepository;
import com.weather.services.UserService;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository repository;
  @Override
  public User findByLogin(String login) {
    return repository.findByLogin(login)
        .orElseThrow(() -> new NoSuchElementException("User with login " + login + " not found."));
  }
}
