package com.project.videoStreaming.Followers.Entity;

import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.UUID;

import com.project.videoStreaming.Followers.DTO.followAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "followers")
public class FollowersEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "followers_id")
    private UUID followersId;

    @Column(name = "channel_id")
    private UUID channelId;

    @Column(name = "action")
    @Enumerated(EnumType.STRING)
    private followAction action;

    @Column(name = "followed_at", insertable = false, updatable = false)
    private LocalDate followedAt;
}
