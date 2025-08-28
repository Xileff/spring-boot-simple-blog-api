package com.felix.simple_blog.configs;

import com.felix.simple_blog.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserRepository repository;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };

//        Lambda
//        return username -> repository.findByEmail(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
