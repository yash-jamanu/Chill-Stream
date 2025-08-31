package com.project.videoStreaming.LikeAndDislike.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.videoStreaming.LikeAndDislike.Entity.EntityDislike;

@Repository
public interface DislikeRepository extends JpaRepository<EntityDislike, UUID> {
    Boolean existsByUserIdAndVideoId(UUID userid, UUID videoid);

    Optional <EntityDislike> findByUserIdAndVideoId (UUID userid, UUID videoid);
}
