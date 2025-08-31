package com.project.videoStreaming.Wishlist.Service;

import java.util.List;
import java.util.UUID;

import com.project.videoStreaming.Video.DTO.Video;
import com.project.videoStreaming.Wishlist.DTO.Wishlist;

public interface WishlistService {
    public List<Video> getVideoByWishlist (UUID userid);

    public void addVideoToWishlist (Wishlist wishlist);
}
