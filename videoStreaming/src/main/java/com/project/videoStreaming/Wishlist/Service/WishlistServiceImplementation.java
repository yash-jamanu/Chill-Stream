package com.project.videoStreaming.Wishlist.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.videoStreaming.Video.DTO.Video;
import com.project.videoStreaming.Video.Entity.VideoEntity;
import com.project.videoStreaming.Video.Repository.VideoRepository;
import com.project.videoStreaming.Wishlist.DTO.Wishlist;
import com.project.videoStreaming.Wishlist.Entity.WishlistEntity;
import com.project.videoStreaming.Wishlist.Repository.WishlistRepository;

@Service
public class WishlistServiceImplementation implements WishlistService {

    @Autowired 
    WishlistRepository wishlistRepository;

    @Autowired 
    VideoRepository videoRepository;

    @Override
    public List<Video> getVideoByWishlist(UUID userid) {
        List <UUID> videoIDs = wishlistRepository.findVideoidByUserid(userid);

        if(videoIDs.isEmpty()){
            return new ArrayList<>();
        }

        List<VideoEntity> videoEntities = videoRepository.findAllById(videoIDs);
            
            List<Video> VideoDetails = new ArrayList<>();
            for(VideoEntity entity : videoEntities){
                Video getVideoDetails = new Video();
                getVideoDetails.setTitle(entity.getTitle());
                getVideoDetails.setThumbnail(entity.getThumbnail());
                getVideoDetails.setFilepath(entity.getFilepath());
                getVideoDetails.setCaption(entity.getCaption());
                getVideoDetails.setCategory(entity.getCategory());
                getVideoDetails.setDescription(entity.getDescription());
                getVideoDetails.setCreated_at(entity.getCreated_at());
                getVideoDetails.setUserId(entity.getUserId());
                getVideoDetails.setVideoId(entity.getVideoId()); 
                VideoDetails.add(getVideoDetails);  
            } 

        return VideoDetails;
    }

    @Override
    public void addVideoToWishlist(Wishlist wishlist) {
        try {
            WishlistEntity entity = new WishlistEntity();
            entity.setUserid(wishlist.getUserid());
            entity.setVideoid(wishlist.getVideoid());
            wishlistRepository.save(entity);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
