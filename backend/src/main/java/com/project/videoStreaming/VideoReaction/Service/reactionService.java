package com.project.videoStreaming.VideoReaction.Service;

import java.util.UUID;

import com.project.videoStreaming.VideoReaction.DTO.videoReaction;

public interface reactionService {

    public videoReaction getReactionStatus (UUID userId, UUID videoId);
    public void videoReaction (videoReaction reaction);
}
