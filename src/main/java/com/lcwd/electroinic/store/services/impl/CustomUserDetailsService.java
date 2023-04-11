package com.lcwd.electroinic.store.services.impl;

import com.lcwd.electroinic.store.entities.User;
import com.lcwd.electroinic.store.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Invalid User Name !!"));
        System.out.println("CustomUserDetailsService is called");
        return user;
    }
}
