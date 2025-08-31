package com.project.videoStreaming.LikeAndDislike.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.videoStreaming.LikeAndDislike.Entity.LikeEntity;
import java.util.Optional;


@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, UUID> {
    Boolean existsByUserIdAndVideoId(UUID userid, UUID videoid);

    Optional <LikeEntity> findByVideoIdAndUserId(UUID videoid, UUID userid);
}
