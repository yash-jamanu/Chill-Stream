package com.project.videoStreaming.Followers.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.project.videoStreaming.Followers.DTO.Follower;
import com.project.videoStreaming.Followers.Service.FollowersServiceImplementation;
import com.project.videoStreaming.SecurityConfig.AuthService;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/follower")
public class FollowersController {

    @Autowired
    FollowersServiceImplementation Service;

    @Autowired
    AuthService auth;

    @PostMapping("/follow-unfollow")
    public void follower(@PathVariable UUID channelId) {
        Follower follower = new Follower();

        follower.setChannelId(channelId);
        follower.setFollowersId(auth.getUserId());
        Service.follower(follower);
    }
    

}
