package com.project.videoStreaming.Video.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.project.videoStreaming.Video.DTO.Video;
import com.project.videoStreaming.Video.Service.VideoServiceImplementation;

import java.util.List;
// import java.util.Map;
import java.util.UUID;

import com.project.videoStreaming.SecurityConfig.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/video")
public class VideoController {
    
    @Autowired
    VideoServiceImplementation implementation;

    @Autowired
    AuthService auth;

    // Get videos uploaded by you.
    @GetMapping("/my-videos")
    public List<Video> getVideosByUserID(){ 
        UUID userid = auth.getUserId();
        return implementation.getVideosByUserID(userid);
    }

    //Get videos uploaded by other users.
    @GetMapping("/user/{channelid}")
    public List<Video> getVideosByChannelID(@PathVariable UUID channelID) {  

        return implementation.getVideosByUserID(channelID);
    }
    

    @GetMapping("/random-videos")
    public List<Video> getRamdonVideos(){
        return implementation.getRandomVideos();
    }

    @GetMapping("/category/{value}")
    public List<Video> getVideosByCategory(@PathVariable String value){
        return implementation.getRandomVideos();
    }

    @PostMapping("/create")
    public void createVideo(@RequestBody Video video) {
        UUID userid = auth.getUserId();
        video.setUserId(userid);
        implementation.createVideo(video);
    }

    @PutMapping("/user/update/{videoid}")
    public void updateVideo(@RequestBody Video video, @PathVariable UUID videoid){
        implementation.updateVideoUsingUserIDAndVideoID(video, auth.getUserId(), videoid);
    }

    @DeleteMapping("/delete/user/video/{videoid}")
    public void deleteVideo(@PathVariable UUID videoid){
        implementation.deleteVideo(auth.getUserId(), videoid);
    }    
}
