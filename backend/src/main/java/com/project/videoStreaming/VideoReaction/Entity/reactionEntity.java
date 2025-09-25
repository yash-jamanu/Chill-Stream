package com.project.videoStreaming.VideoReaction.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.project.videoStreaming.VideoReaction.DTO.reaction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "video_reaction")
public class reactionEntity {

    @Id
    @Column(name= "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID Id;

    @Column(name = "userid")
    private UUID userId;

    @Column (name = "videoid")
    private UUID videoId;

    @Column (name = "reaction")
    @Enumerated(EnumType.STRING)
    private reaction reactionType;

    @Column(name = "reacted_at")
    private LocalDateTime reactedAt;

}
