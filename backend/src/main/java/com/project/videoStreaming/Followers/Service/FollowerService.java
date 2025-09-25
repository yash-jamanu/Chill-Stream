package com.project.videoStreaming.Followers.Service;

import java.util.UUID;

import com.project.videoStreaming.Followers.DTO.Follower;

public interface FollowerService {

    public Follower getfollowStatus (UUID followersId, UUID channelId);

    public void follower(Follower follower);

}
