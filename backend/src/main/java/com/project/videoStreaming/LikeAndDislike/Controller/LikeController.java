package com.project.videoStreaming.LikeAndDislike.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.videoStreaming.LikeAndDislike.Service.LikeServiceImplementation;
import com.project.videoStreaming.SecurityConfig.AuthService;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.project.videoStreaming.LikeAndDislike.DTO.Like;
import com.project.videoStreaming.LikeAndDislike.DTO.Dislike;

@RestController
@RequestMapping("/video")
public class LikeController {

    @Autowired
    LikeServiceImplementation implementation;

    @Autowired
    AuthService auth;

    @GetMapping("/like")
    public void insertLike(@PathVariable UUID videoid) {
        Like like = new Like();
        like.setUserId(auth.getUserId());
        like.setVideoId(videoid);
        implementation.insertLike(like);
    }
    
    @GetMapping("/dislike")
    public void insertdisLike(@PathVariable UUID videoid) {
        Dislike dislike = new Dislike();
        dislike.setUserId(auth.getUserId());
        dislike.setVideoId(videoid);
        implementation.insertDislike(dislike);
    }
}
