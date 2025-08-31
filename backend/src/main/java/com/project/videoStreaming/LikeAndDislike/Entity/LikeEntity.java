package com.project.videoStreaming.LikeAndDislike.Entity;

import jakarta.persistence.Table;

import java.security.Timestamp;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="likes")
public class LikeEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "userid")
    private UUID userId;

    @Column(name = "videoid")
    private UUID videoId;

    @Column (name = "liked_at")
    private Timestamp liked_at;
}
