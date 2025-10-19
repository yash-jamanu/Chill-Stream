package com.project.videoStreaming.VideoReaction.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.videoStreaming.Video.Service.VideoServiceImplementation;
import com.project.videoStreaming.VideoReaction.DTO.reaction;
import com.project.videoStreaming.VideoReaction.DTO.videoReaction;
import com.project.videoStreaming.VideoReaction.Entity.reactionEntity;
import com.project.videoStreaming.VideoReaction.Repository.reactionRepository;

@Service
public class reactionServiceImp implements reactionService {

    @Autowired
    reactionRepository repository;

    @Autowired
    VideoServiceImplementation videoImp;

    @Override
    public void videoReaction(videoReaction videoReaction) {
    try {
        Optional<reactionEntity> existingReactionOpt =
                repository.findTopByUserIdAndVideoIdOrderByReactionTimeDesc(videoReaction.getUserId(), videoReaction.getVideoId());

        if (existingReactionOpt.isPresent()) {
            reactionEntity existingReaction = existingReactionOpt.get();

            // User removes its reaction from the video
            // If the user had already liked the video and now clicks again to remove the like 
            // It removes the reaction and makes it "NONE" 
            if (existingReaction.getReactionType() == videoReaction.getReactionType()) {
                if (videoReaction.getReactionType() == reaction.LIKE) {
                    videoImp.decrementLike(videoReaction.getVideoId());
                } else {
                    videoImp.decrementDisLike(videoReaction.getVideoId());
                }
                repository.deleteByUserIdAndVideoId(videoReaction.getUserId(), videoReaction.getVideoId());

                return;
            }

            // Case 2: User switched reaction (like -> dislike OR dislike -> like)
            if (existingReaction.getReactionType() == reaction.LIKE &&
                videoReaction.getReactionType() == reaction.DISLIKE) {
                videoImp.decrementLike(videoReaction.getVideoId());
                videoImp.incrementDisLike(videoReaction.getVideoId());
            } else if (existingReaction.getReactionType() == reaction.DISLIKE &&
                       videoReaction.getReactionType() == reaction.LIKE) {
                videoImp.decrementDisLike(videoReaction.getVideoId());
                videoImp.incrementLike(videoReaction.getVideoId());
            }

            // Update existing reaction
            existingReaction.setReactionType(videoReaction.getReactionType());
            existingReaction.setReactionTime(LocalDateTime.now());
            repository.save(existingReaction);

        } else {
            // Case 3: First time reaction
            reactionEntity newReaction = new reactionEntity();
            newReaction.setUserId(videoReaction.getUserId());
            newReaction.setVideoId(videoReaction.getVideoId());
            newReaction.setReactionType(videoReaction.getReactionType());
            newReaction.setReactionTime(LocalDateTime.now());

            if (videoReaction.getReactionType() == reaction.LIKE) {
                videoImp.incrementLike(videoReaction.getVideoId());
            } else {
                videoImp.incrementDisLike(videoReaction.getVideoId());
            }

            repository.save(newReaction);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}


    @Override
    public videoReaction getReactionStatus(UUID userId, UUID videoId) {
        Optional <reactionEntity> getReaction = repository.findTopByUserIdAndVideoIdOrderByReactionTimeDesc(userId, videoId);
        
        videoReaction reaction = new videoReaction();
        
        if(getReaction.isPresent()){
            reactionEntity entity = getReaction.get();
            BeanUtils.copyProperties(entity, reaction);
        }
        return reaction;
    }

}
