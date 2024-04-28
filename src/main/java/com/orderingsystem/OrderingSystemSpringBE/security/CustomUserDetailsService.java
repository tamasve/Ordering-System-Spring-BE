package com.orderingsystem.OrderingSystemSpringBE.security;

import com.orderingsystem.OrderingSystemSpringBE.entity.UserData;
import com.orderingsystem.OrderingSystemSpringBE.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDataRepository userDataRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData user = userDataRepository.findByUsername(username);
        if (user == null)  throw new UsernameNotFoundException(username);
        return new CustomUserDetails( user );
    }
}
