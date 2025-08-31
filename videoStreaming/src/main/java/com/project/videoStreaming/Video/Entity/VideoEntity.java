package com.project.videoStreaming.Video.Entity;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "videos")
public class VideoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "videoid", updatable = false, nullable = false)
    private UUID videoId;

    @Column(name = "userid")
    private UUID userId;

    @Column(name = "title")
    private String title;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "filepath")
    private String filepath;

    @Column(name = "caption")
    private String caption;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private String category;

    @Column(name = "videostatus")
    private String videostatus;

    @Column (name = "likecount")
    private int likecount;

    @Column(name = "dislike")
    private int dislike;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp created_at;
}
