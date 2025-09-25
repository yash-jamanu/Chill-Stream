package com.project.videoStreaming.VideoReaction.DTO;

import java.sql.Timestamp;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class videoReaction {
    private UUID Id;
    private UUID userId;
    private UUID videoId;
    private reaction reactionType;
    private Timestamp reacted_at;
}
