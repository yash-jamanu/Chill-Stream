package com.project.videoStreaming.SecurityConfig;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.project.videoStreaming.Users.Entity.UserEntity;
import com.project.videoStreaming.Users.Repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    UserRepository repo;

    public UUID getUserId(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return repo.findByEmail(auth.getName())
               .map(UserEntity::getUserId)
               .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
