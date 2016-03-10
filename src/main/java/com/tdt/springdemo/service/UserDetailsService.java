package com.tdt.springdemo.service;

import com.tdt.springdemo.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Custom user details service
 */
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.tdt.springdemo.model.User user = userDAO.findByEmail(username);
        return new User(user.getEmail(), user.getPassword(), Arrays.asList(new GrantedAuthority[]{new SimpleGrantedAuthority("USER")}));
    }
}
