package com.project.videoStreaming.LikeAndDislike.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.videoStreaming.LikeAndDislike.DTO.Dislike;
import com.project.videoStreaming.LikeAndDislike.DTO.Like;
import com.project.videoStreaming.LikeAndDislike.Entity.EntityDislike;
import com.project.videoStreaming.LikeAndDislike.Entity.LikeEntity;
import com.project.videoStreaming.LikeAndDislike.Repository.DislikeRepository;
import com.project.videoStreaming.LikeAndDislike.Repository.LikeRepository;
import com.project.videoStreaming.Video.Entity.VideoEntity;
import com.project.videoStreaming.Video.Repository.VideoRepository;

@Service
public class LikeServiceImplementation implements LikeService{

    @Autowired
    LikeRepository likeRepository;
    @Autowired
    DislikeRepository dislikeRepository;
    @Autowired
    VideoRepository videoRepository;


    @Override
    public void insertLike(Like like) {

        try{
            if(dislikeRepository.existsByUserIdAndVideoId(like.getUserId(), like.getVideoId())){
                Optional <EntityDislike> getDislike = dislikeRepository.findByUserIdAndVideoId(like.getUserId(), like.getVideoId());

                if(getDislike.isPresent()){
                    EntityDislike entity = getDislike.get();
                    dislikeRepository.deleteById(entity.getId());

                    Optional <VideoEntity> getVideo = videoRepository.findByVideoId(like.getVideoId()); 
                    if(getVideo.isPresent()){
                        VideoEntity updateEntity = getVideo.get();
                        updateEntity.setDislike(updateEntity.getDislike()-1);
                        videoRepository.save(updateEntity);
                    }
                }
            }
            if(!likeRepository.existsByUserIdAndVideoId(like.getUserId(), like.getVideoId())){
                LikeEntity entity = new LikeEntity();
                entity.setUserId(like.getUserId());
                entity.setVideoId(like.getVideoId());
                likeRepository.save(entity);

                Optional<VideoEntity> videoEntity = videoRepository.findByVideoId(like.getVideoId());
                if (videoEntity.isPresent()) {
                    VideoEntity entity1 = videoEntity.get();
                    entity1.setLikecount(entity1.getLikecount()+1);
                    videoRepository.save(entity1);
                }
            }else{
                Optional <LikeEntity> entity = likeRepository.findByVideoIdAndUserId(like.getVideoId(), like.getUserId());
                if(entity.isPresent()){
                    LikeEntity getLike = entity.get();
                    likeRepository.deleteById(getLike.getId());

                    Optional <VideoEntity> getVideo = videoRepository.findByVideoId(like.getVideoId());
                    if(getVideo.isPresent()){
                        VideoEntity getVideoEntity = getVideo.get();
                        getVideoEntity.setLikecount(getVideoEntity.getLikecount()-1);
                        videoRepository.save(getVideoEntity);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }

    public void insertDislike (Dislike addDislike){

        try {
            
            if(likeRepository.existsByUserIdAndVideoId(addDislike.getUserId(), addDislike.getVideoId())){
                Optional <LikeEntity> getlike = likeRepository.findByVideoIdAndUserId(addDislike.getVideoId(), addDislike.getUserId());
                if (getlike.isPresent()) {
                    LikeEntity entity = getlike.get();
                    likeRepository.deleteById(entity.getId());

                    Optional <VideoEntity> getVideo = videoRepository.findByVideoId(addDislike.getVideoId());
                    if (getVideo.isPresent()) {
                        VideoEntity getVideoEntity = getVideo.get();
                        getVideoEntity.setLikecount(getVideoEntity.getLikecount()-1);
                        videoRepository.save(getVideoEntity);
                    } 
                }
            }
            
            if(!dislikeRepository.existsByUserIdAndVideoId(addDislike.getUserId(), addDislike.getVideoId())){
                EntityDislike entity = new EntityDislike();
                entity.setUserId(addDislike.getUserId());
                entity.setVideoId(addDislike.getVideoId());
                dislikeRepository.save(entity);

                Optional<VideoEntity> videoEntity = videoRepository.findByVideoId(addDislike.getVideoId());
                if (videoEntity.isPresent()) {
                    VideoEntity entity1 = videoEntity.get();
                    entity1.setDislike(entity1.getDislike()+1);
                    videoRepository.save(entity1);
                }
            }else{
                Optional <EntityDislike> getDislike = dislikeRepository.findByUserIdAndVideoId(addDislike.getUserId(), addDislike.getVideoId());
                if(getDislike.isPresent()){
                    EntityDislike getDislikeEntity = getDislike.get();
                    dislikeRepository.deleteById(getDislikeEntity.getId());
                
                    Optional <VideoEntity> updateVideo = videoRepository.findByVideoId(addDislike.getVideoId());
                    if(updateVideo.isPresent()){
                        VideoEntity getVideoEntity = updateVideo.get();
                        getVideoEntity.setDislike(getVideoEntity.getDislike()-1);
                        videoRepository.save(getVideoEntity);
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


}
