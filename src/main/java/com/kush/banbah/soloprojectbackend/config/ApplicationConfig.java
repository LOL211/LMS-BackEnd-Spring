package com.kush.banbah.soloprojectbackend.config;

import com.kush.banbah.soloprojectbackend.User.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepo repository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repository.findbyEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    }

}
