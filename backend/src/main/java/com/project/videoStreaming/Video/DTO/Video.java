package com.project.videoStreaming.Video.DTO;

import java.sql.Timestamp;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    private UUID videoId;
    private UUID userId;
    private String title;
    private String thumbnail;
    private String filepath;
    private String caption;
    private String description;
    private String category;
    private String videostatus;
    private int likecount;
    private int dislike;
    private Timestamp created_at;
}
