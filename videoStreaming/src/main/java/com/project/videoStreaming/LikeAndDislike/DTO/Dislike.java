package com.project.videoStreaming.LikeAndDislike.DTO;


import java.security.Timestamp;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dislike {
    private UUID id;
    private UUID userId;
    private UUID videoId;
    private Timestamp disliked_at;
}