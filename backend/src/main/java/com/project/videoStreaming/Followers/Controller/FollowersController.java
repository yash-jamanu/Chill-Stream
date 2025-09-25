package com.project.videoStreaming.Followers.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.project.videoStreaming.Followers.DTO.Follower;
import com.project.videoStreaming.Followers.Service.FollowersServiceImplementation;
import com.project.videoStreaming.SecurityConfig.AuthService;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/follower")
public class FollowersController {

    @Autowired
    FollowersServiceImplementation Service;

    @Autowired
    AuthService auth;

    @GetMapping("/status/follow/{channelId}")
    public Follower getFollwerStatus(@PathVariable UUID channelId) {
        UUID followersId = auth.getUserId();
        return Service.getfollowStatus(followersId, channelId);
    }
    

    @PostMapping("/follow-unfollow")
    public void follower(@RequestBody Follower follower) {
        follower.setFollowersId(auth.getUserId());
        Service.follower(follower);
    }
    

}
