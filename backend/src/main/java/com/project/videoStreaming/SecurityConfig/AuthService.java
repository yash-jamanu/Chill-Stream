package com.project.videoStreaming.SecurityConfig;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.project.videoStreaming.Users.Entity.UserEntity;
import com.project.videoStreaming.Users.Repository.UserRepository;
import com.project.videoStreaming.Users.Service.userDetailsService;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    userDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    public UUID getUserId(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(auth.getName())
               .map(UserEntity::getUserId)
               .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserDetails authenticate (String email, String password){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        return userDetailsService.loadUserByUsername(email);
    }

    
}
