package com.project.videoStreaming.VideoReaction.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.videoStreaming.VideoReaction.Entity.reactionEntity;

@Repository
public interface reactionRepository extends JpaRepository<reactionEntity, UUID>{

    Optional <reactionEntity> findTopByUserIdAndVideoIdOrderByReactionTimeDesc(UUID userId, UUID videoId);


}
