package com.project.videoStreaming.Video.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.videoStreaming.Video.Entity.VideoEntity;

import java.util.List;
import java.util.Optional;


@Repository
public interface VideoRepository extends JpaRepository<VideoEntity, UUID> {
    Optional<VideoEntity> findByTitle(String title);

    List<VideoEntity> findByCategory(String category);

    List<VideoEntity> findByUserId(UUID userid);

    Optional <VideoEntity> findByUserIdAndVideoId(UUID userid, UUID videoid);

    Optional <VideoEntity> findByVideoId (UUID videoid);
}
