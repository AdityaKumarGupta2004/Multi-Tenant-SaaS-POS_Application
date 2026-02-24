package com.trisys.Pos.System.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserImplementation  implements UserDetailsService {
    

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
