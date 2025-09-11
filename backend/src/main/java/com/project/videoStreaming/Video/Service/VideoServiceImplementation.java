package com.project.videoStreaming.Video.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.videoStreaming.Video.DTO.Video;
import com.project.videoStreaming.Video.Entity.VideoEntity;
import com.project.videoStreaming.Video.Repository.VideoRepository;

@Service
public class VideoServiceImplementation implements VideoService {

    @Autowired
    VideoRepository Repository;

    @Override
    public List<Video> searchVideos(String searchText){

        List<VideoEntity> getVideos =  Repository.findVideosBySearchText(searchText);

        List <Video> listOfVideos = new ArrayList<>();
        
        if(!getVideos.isEmpty()){
            for(VideoEntity entity : getVideos ){
                Video video = new Video();
                BeanUtils.copyProperties(entity, video);
                listOfVideos.add(video);
            }
        }

        return listOfVideos;
    }

    @Override
    public void createVideo(Video video) {
        try {
            VideoEntity entity = new VideoEntity();
            entity.setUserId(video.getUserId());
            entity.setTitle(video.getTitle());
            entity.setThumbnail(video.getThumbnail());
            entity.setFilepath(video.getFilepath());
            entity.setCaption(video.getCaption());
            entity.setDescription(video.getDescription());
            entity.setCategory(video.getCategory());
            entity.setVideostatus(video.getVideostatus());
            System.out.println(entity);
            Repository.save(entity);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Video> getVideosByUserID(UUID userid) {

        List<VideoEntity> getVideos = Repository.findByUserId(userid);
        
        List<Video> videos = new  ArrayList<>();
        
        for (VideoEntity entity : getVideos) {
            Video video = new Video();
            BeanUtils.copyProperties(entity, video);
            videos.add(video);
        }
        return videos;
    }
    

    @Override
    public List<Video> getRandomVideos(){
        
        List<VideoEntity> getVideos = Repository.findAll(); 

        List<Video> videos = new ArrayList<>();

        for(VideoEntity entity : getVideos){
            Video video = new Video();
            
            if("public".equals(entity.getVideostatus())){
                BeanUtils.copyProperties(entity, video);
                videos.add(video);
            }   
        }
        return videos; 
    }

    @Override
    public List<Video> getVideosByCategory(String value){
        List<VideoEntity> getVideos = Repository.findByCategory(value); 

        List<Video> videos = new ArrayList<>();

        for(VideoEntity entity : getVideos){
            Video video = new Video();
            
            if("public".equals(entity.getVideostatus())){
                BeanUtils.copyProperties(entity, video);
                videos.add(video);
            }   
        }
        return videos;
    }

    @Override
    public void updateVideoUsingUserIDAndVideoID(Video video, UUID userid, UUID videoid) {
        
        Optional <VideoEntity> getVideo = Repository.findByUserIdAndVideoId(userid, videoid);

        if(getVideo.isPresent()){
            VideoEntity entity = getVideo.get();

            if(video.getTitle() != null) entity.setTitle(video.getTitle());
            if(video.getThumbnail() != null) entity.setThumbnail(video.getThumbnail());
            if(video.getCaption() != null) entity.setCaption(video.getCaption());
            if(video.getDescription() != null) entity.setDescription(video.getDescription());
            if(video.getCategory() != null) entity.setCategory(video.getCategory());
            if(video.getVideostatus() != null) entity.setVideostatus(video.getVideostatus());
            Repository.save(entity);
        }
    }

    @Override
    public void deleteVideo(UUID userid, UUID videoid) {
        Optional <VideoEntity> getVideo = Repository.findByUserIdAndVideoId(userid, videoid);

        if(getVideo.isPresent()){
            Repository.deleteById(videoid);
        }
    }

    
    
}
