package com.weather.security;

import com.weather.model.entity.User;
import com.weather.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserService service;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return new UserPrincipal(service.findByLogin(username));
    }
}
