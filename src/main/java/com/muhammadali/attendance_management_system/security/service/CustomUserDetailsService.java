package com.muhammadali.attendance_management_system.security.service;

import com.muhammadali.attendance_management_system.model.User;
import com.muhammadali.attendance_management_system.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(
            UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Optional<User> userInfo =
                userRepository.findByEmail(username);

        if(userInfo.isEmpty()){
            throw new UsernameNotFoundException(
                    "User not found: " + username
            );
        }

        User user = userInfo.get();
        return new UsersDetails(user);
    }


}