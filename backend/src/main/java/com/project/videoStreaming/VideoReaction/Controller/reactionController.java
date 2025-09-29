package com.project.videoStreaming.VideoReaction.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.project.videoStreaming.SecurityConfig.AuthService;
import com.project.videoStreaming.VideoReaction.DTO.videoReaction;
import com.project.videoStreaming.VideoReaction.Service.reactionServiceImp;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/video")
public class reactionController {

    @Autowired
    AuthService auth;

    @Autowired
    reactionServiceImp service;

    @GetMapping("/status/reaction/{videoId}")
    public videoReaction getReactionStatus(@PathVariable UUID videoId) {

        UUID userId = auth.getUserId();
        return service.getReactionStatus(userId, videoId);
    }

    @PostMapping("/videos/reaction")
    public void videoReaction(@RequestBody videoReaction VideoReaction) {
        UUID userId = auth.getUserId();
        VideoReaction.setUserId(userId);
        service.videoReaction(VideoReaction);
    }
    
        
}
