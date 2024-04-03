package com.github.library.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Slf4j
public class SecurityConfiguration {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        try {
            return httpSecurity
                    .authorizeHttpRequests(registry -> registry
                            .requestMatchers("ui/**").hasAuthority("admin")
                            .requestMatchers("ui/books/**").hasAnyAuthority("admin", "user")
                            .anyRequest().denyAll()
                    )
                    .formLogin(Customizer.withDefaults())
                    .build();
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }
}
