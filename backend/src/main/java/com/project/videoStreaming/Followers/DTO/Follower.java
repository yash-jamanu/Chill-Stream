package com.project.videoStreaming.Followers.DTO;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Follower {
    private UUID Id;
    private UUID followersId;
    private UUID channelId;
    private followAction action;
    private LocalDate followed_at;
}
