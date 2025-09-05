package com.project.videoStreaming.Users.Controller;

// import java.lang.Math;
import org.springframework.web.bind.annotation.RestController;

import com.project.videoStreaming.SecurityConfig.AuthService;
import com.project.videoStreaming.Users.DTO.User;
import com.project.videoStreaming.Users.Service.UserServiceImplementation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/user")
public class UserDetailsController {

    @Autowired
    UserServiceImplementation Service;

    @Autowired
    AuthService auth;

    @GetMapping("/details")
    public User getUserDetails() {
        User user =  Service.getUserDetails(auth.getUserId());
        return user;
    }
    

    @PutMapping("/update")
    public void updateUserDetails(@RequestBody User user) {
        Service.updateUserDetails(user);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestBody User request) {
        String email = request.getEmail();
        String password = request.getPassword();
        Service.deleteUser(email, password);
    }
    
}
