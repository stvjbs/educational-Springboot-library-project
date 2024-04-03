package com.github.library.config.security;

import com.github.library.entity.Client;
import com.github.library.exceptions.NotFoundEntityException;
import com.github.library.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Finding: " + username);
        Client client = clientRepository.findClientByLogin(username).orElseThrow(NotFoundEntityException::new);
        log.info(username + " was found.");

        return new User(client.getLogin(), client.getPassword(), List.of(
                new SimpleGrantedAuthority(client.getRole())
        ));
    }
}
