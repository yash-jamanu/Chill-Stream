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



@RestController
@RequestMapping("/video")
public class reactionController {

    @Autowired
    AuthService auth;

    @Autowired
    reactionServiceImp service;

    @GetMapping("/status/reaction")
    public videoReaction getReactionStatus(UUID videoId) {
        UUID userId = auth.getUserId();
        return service.getReactionStatus(userId, videoId);
    }

    @PostMapping("/reaction")
    public void videoReaction(@PathVariable videoReaction VideoReaction) {
        UUID userId = auth.getUserId();
        VideoReaction.setUserId(userId);
        service.videoReaction(VideoReaction);
    }
    
        
}
