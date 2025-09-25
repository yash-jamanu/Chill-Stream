package com.project.videoStreaming.VideoReaction.Service;
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
                repository.findTopByUserIdAndVideoIdOrderByReactedAtDesc(videoReaction.getUserId(), videoReaction.getVideoId());

        if (existingReactionOpt.isPresent()) {
            reactionEntity existingReaction = existingReactionOpt.get();

            if (existingReaction.getReactionType() == videoReaction.getReactionType()) {
                if (videoReaction.getReactionType() == reaction.LIKE) {
                    videoImp.decrementLike(videoReaction.getVideoId());
                } else {
                    videoImp.decrementDisLike(videoReaction.getVideoId());
                }
                repository.delete(existingReaction);
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
            repository.save(existingReaction);

        } else {
            // Case 3: First time reaction
            reactionEntity newReaction = new reactionEntity();
            BeanUtils.copyProperties(videoReaction, newReaction);

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
        Optional <reactionEntity> getReaction = repository.findTopByUserIdAndVideoIdOrderByReactedAtDesc(userId, videoId);
        videoReaction reaction = new videoReaction();
        if(getReaction.isPresent()){
            reactionEntity entity = getReaction.get();
            BeanUtils.copyProperties(entity, reaction);
            return reaction;
        }
        return null;
    }

}
