package com.project.videoStreaming.VideosTest;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.videoStreaming.Video.DTO.Video;
import com.project.videoStreaming.Video.Entity.VideoEntity;
import com.project.videoStreaming.Video.Repository.VideoRepository;
import com.project.videoStreaming.Video.Service.VideoServiceImplementation;

@SpringBootTest
public class VideoServicesTest {
    
    @Autowired
    VideoServiceImplementation implementation;

    @Autowired
    VideoRepository repository;

    @Test
    void createVideo(){
        //Arange
        Video video = Video.builder()
        .title("My third video")
        .thumbnail("src/home/img/img534.png")
        .filepath("src/home/video/video13322.vid")
        .caption("this is my third video")
        .description("my third video is about the lizard an my home.")
        .category("Reptiles")
        .videostatus("public")
        .build();

        //Act
        implementation.createVideo(video);

        Optional<VideoEntity> getVideo = repository.findByTitle(video.getTitle());

        Assertions.assertThat(getVideo.get().getCaption()).isEqualTo("this is my third video");
    }

    @Test
    void getVideosUsingUserId(){
        
    }
}
