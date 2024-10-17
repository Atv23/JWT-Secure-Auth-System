package com.jwt.service;

import java.util.ArrayList;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		if (username.equals("Atv")) {
            // Use BCryptPasswordEncoder directly for encoding
            String encodedPassword = new BCryptPasswordEncoder().encode("Atv@@2303");
            return new User("Atv", encodedPassword, new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("Username not found");
        }
		
	}

}
