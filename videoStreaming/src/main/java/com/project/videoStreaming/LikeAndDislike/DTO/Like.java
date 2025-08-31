package com.project.videoStreaming.LikeAndDislike.DTO;

import java.sql.Timestamp;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    private UUID id;
    private UUID userId;
    private UUID videoId;
    private Timestamp liked_at;
}
