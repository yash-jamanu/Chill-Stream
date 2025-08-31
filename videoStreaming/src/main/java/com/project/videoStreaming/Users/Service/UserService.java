package com.project.videoStreaming.Users.Service;


import com.project.videoStreaming.Users.DTO.User;

public interface UserService {

    public void userRegistration(User user);

    public User getUserDetails(String email);

    public void updateUserDetails(User user);

    public void deleteUser(String email, String password);
}
