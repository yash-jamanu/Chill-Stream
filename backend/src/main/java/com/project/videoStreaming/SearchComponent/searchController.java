package com.project.videoStreaming.SearchComponent;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.videoStreaming.Video.DTO.Video;
import com.project.videoStreaming.Video.Service.VideoServiceImplementation;

@RestController
public class searchController {

    @Autowired
    VideoServiceImplementation implementation;

    @GetMapping("/search")
     public ResponseEntity<List<Video>> searchArticles(@RequestParam String searchText) {
        List<Video> foundVideo = implementation.searchVideos(searchText);
        if (!foundVideo.isEmpty()) {
            return ResponseEntity.ok(foundVideo);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
