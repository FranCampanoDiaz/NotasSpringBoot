package com.example.notes.repository.service.service;

import com.example.notes.model.AppUser;
import com.example.notes.repository.service.AppUserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public CustomUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(appUser.getPassword())
                .authorities(new SimpleGrantedAuthority(appUser.getRole()))
                .build();
    }


}
