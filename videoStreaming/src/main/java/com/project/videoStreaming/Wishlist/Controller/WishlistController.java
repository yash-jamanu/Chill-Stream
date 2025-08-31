package com.project.videoStreaming.Wishlist.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.project.videoStreaming.SecurityConfig.AuthService;
import com.project.videoStreaming.Video.DTO.Video;
import com.project.videoStreaming.Wishlist.DTO.Wishlist;
import com.project.videoStreaming.Wishlist.Service.WishlistServiceImplementation;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class WishlistController {

    @Autowired
    WishlistServiceImplementation implementation;

    @Autowired
    AuthService auth;

    @GetMapping("/wishlist/videos")
    public List<Video> getVideosByWishlist() {

       return implementation.getVideoByWishlist(auth.getUserId());
    }
    
    @PostMapping("/wishlist")
    public void addVideoToWishlist(@PathVariable UUID videoid) {

        UUID userid = auth.getUserId();
        
        Wishlist wishlist = new Wishlist();

        wishlist.setVideoid(videoid);
        wishlist.setUserid(userid);

        implementation.addVideoToWishlist(wishlist);
    }
    
    
}
