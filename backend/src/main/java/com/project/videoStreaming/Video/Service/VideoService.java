package com.project.videoStreaming.Video.Service;

import java.util.List;
import java.util.UUID;

import com.project.videoStreaming.Video.DTO.Video;

public interface VideoService {

    Video getVideoByVideoId(UUID videoId);

    List<Video> searchVideos(String searchText);
    
    public void createVideo(Video video);

    public List<Video> getVideosByUserID(UUID userId); // videos uploaded by you and other users.

    public List<Video> getRandomVideos ();

    public List<Video> getVideosByCategory(String value);

    public void updateVideoUsingUserIDAndVideoID(Video video, UUID userId, UUID videoId);

    public void incrementLike (UUID videoId);
    public void decrementLike (UUID videoId);

    public void incrementDisLike (UUID videoId);
    public void decrementDisLike (UUID videoId);

    public void deleteVideo(UUID userid, UUID videoId);
}
