package com.project.videoStreaming.Followers.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.videoStreaming.Followers.Entity.FollowersEntity;
import java.util.Optional;


@Repository
public interface FollowerRepository extends JpaRepository<FollowersEntity, UUID>{

    Optional<FollowersEntity> findTopByFollowersIdAndChannelIdOrderByFollowedAtDesc(UUID followersId, UUID channelId);

    Optional<FollowersEntity> findByFollowersIdAndChannelId(UUID followersId, UUID channelId);

    boolean existsByChannelIdAndFollowersId(UUID channelId, UUID followersId);

    Optional <FollowersEntity> findByChannelIdAndFollowersId(UUID channel_id, UUID followersid);
}
