package com.project.videoStreaming.Users.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.videoStreaming.Users.DTO.userUserDetails;
import com.project.videoStreaming.Users.Entity.UserEntity;
import com.project.videoStreaming.Users.Repository.UserLoginRepository;

@Service
public class userDetailsService implements UserDetailsService {

    @Autowired
    UserLoginRepository loginRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       UserEntity user = loginRepository.findByEmail(email);
       
       return new userUserDetails(user);
    }


}
