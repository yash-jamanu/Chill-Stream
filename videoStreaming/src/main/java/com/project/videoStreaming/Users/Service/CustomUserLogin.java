package com.project.videoStreaming.Users.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.videoStreaming.Users.DTO.UserDetailsIMP;
import com.project.videoStreaming.Users.Entity.UserEntity;
import com.project.videoStreaming.Users.Repository.UserLoginRepository;

@Service
public class CustomUserLogin implements UserDetailsService {

    @Autowired
    UserLoginRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       UserEntity user = repo.findByEmail(email); 
       if (user == null) {
            throw new UsernameNotFoundException("No user found with email: " + email);
        }
       return new UserDetailsIMP(user);
    }


}
