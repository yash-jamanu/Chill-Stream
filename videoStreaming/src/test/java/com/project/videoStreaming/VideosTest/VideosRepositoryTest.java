package com.project.videoStreaming.VideosTest;


import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.videoStreaming.Video.DTO.Video;
import com.project.videoStreaming.Video.Entity.VideoEntity;
import com.project.videoStreaming.Video.Repository.VideoRepository;

@SpringBootTest
public class VideosRepositoryTest {

    @Autowired
    VideoRepository repository;

    @Test
    void CreateVideo(){
        //Arrange
		VideoEntity SaveVideoDetails = VideoEntity.builder()
		.userId(java.util.UUID.fromString("1096b9a6-791b-4372-ab30-2373c01f3be8"))
		.title("My first video")
		.thumbnail("src/home/img/img1.png")
		.filepath("src/home/video/video1.vid")
		.caption("Welcome to my first video")
		.description("My First video is about my beloved Dog")
		.category("Animals")
		.videostatus("private").build();

		//Act
		VideoEntity CreateVideo = repository.save(SaveVideoDetails);

		//Assert 
		Assertions.assertThat(CreateVideo).isNotNull();
        Assertions.assertThat(CreateVideo.getUserId()).isEqualTo(java.util.UUID.fromString("1096b9a6-791b-4372-ab30-2373c01f3be8")).isNotNull();
		Assertions.assertThat(CreateVideo.getTitle()).isEqualTo("My first video").isNotNull();
		Assertions.assertThat(CreateVideo.getThumbnail()).isEqualTo("src/home/img/img1.png").isNotNull();
		Assertions.assertThat(CreateVideo.getFilepath()).isEqualTo("src/home/video/video1.vid").isNotNull();
		Assertions.assertThat(CreateVideo.getCaption()).isEqualTo("Welcome to my first video").isNotNull();
		Assertions.assertThat(CreateVideo.getDescription()).isEqualTo("My First video is about my beloved Dog").isNotNull();
		Assertions.assertThat(CreateVideo.getCategory()).isEqualTo("Animals").isNotNull();
        Assertions.assertThat(CreateVideo.getVideostatus()).isEqualTo("private").isNotNull();
	}

	@Test
	void getVideosUsingUserid(){
		String userid = "1096b9a6-791b-4372-ab30-2373c01f3be8";

		List<VideoEntity> getVideos = repository.findByUserId(java.util.UUID.fromString(userid));
        
        List<Video> videos = new  ArrayList<>();
        
        for (VideoEntity entity : getVideos) {
            Video video = new Video();
            BeanUtils.copyProperties(entity, video);
            videos.add(video);
        }
		
		Assertions.assertThat(videos.get(0).getTitle()).isNotNull();
		Assertions.assertThat(videos.get(0).getTitle()).isEqualTo("My first video");
		Assertions.assertThat(videos.get(0).getThumbnail()).isNotNull();
		Assertions.assertThat(videos.get(0).getFilepath()).isNotNull();
		Assertions.assertThat(videos.get(0).getUserId()).isNotNull();
		Assertions.assertThat(videos.get(0).getCaption()).isNotNull();
		Assertions.assertThat(videos.get(0).getDescription()).isNotNull();
		Assertions.assertThat(videos.get(0).getCategory()).isNotNull();

	}	
    
}
