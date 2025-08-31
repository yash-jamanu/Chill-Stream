package com.project.videoStreaming.Users.Controller;

// import java.lang.Math;
import org.springframework.web.bind.annotation.RestController;

import com.project.videoStreaming.Users.DTO.User;
import com.project.videoStreaming.Users.Service.UserServiceImplementation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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


    @GetMapping("/details")
    public User getUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return Service.getUserDetails(email);
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
