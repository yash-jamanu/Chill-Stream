package com.project.videoStreaming.Comments.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.videoStreaming.Comments.Entity.CommentEntity;
@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {
   
    List<CommentEntity> findByVideoid(UUID videoid);

    
    Optional <CommentEntity> findByUseridAndVideoid(UUID userid, UUID videoid);

    Optional<CommentEntity> findByCommentidAndUseridAndVideoid(UUID commentid, UUID userid, UUID videoid);
}
