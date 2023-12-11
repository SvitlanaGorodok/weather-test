package com.weather.controller;

import com.weather.model.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {

  private final BCryptPasswordEncoder encoder;

  @PostMapping("/auth")
  @ResponseStatus(HttpStatus.OK)
  public void getLogin(@RequestBody User user,
      HttpServletRequest request) {
    String username = user.getLogin();
    String password = user.getPassword();
    try {
      request.login(username, password);
    } catch (ServletException e) {
      e.printStackTrace();
      throw new NoSuchElementException("Bad credentials.");
    }
    log.info("User " + username + " logged in.");
  }
}
