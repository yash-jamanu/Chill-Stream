package com.project.videoStreaming.Followers.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.videoStreaming.Followers.DTO.Follower;
import com.project.videoStreaming.Followers.DTO.followAction;
import com.project.videoStreaming.Followers.Entity.FollowersEntity;
import com.project.videoStreaming.Followers.Repository.FollowerRepository;
import com.project.videoStreaming.Users.Service.UserServiceImplementation;

@Service
public class FollowersServiceImplementation implements FollowerService {

    @Autowired
    FollowerRepository followerRepository;

    @Autowired
    UserServiceImplementation userImp;


    @Override
    public Follower getfollowStatus (UUID followersId, UUID channelId){
        Optional <FollowersEntity> getAction = followerRepository.findTopByFollowersIdAndChannelIdOrderByFollowedAtDesc(followersId, channelId);
        
        Follower action = new Follower();

        if(getAction.isPresent()){
            FollowersEntity entity = getAction.get();
            BeanUtils.copyProperties(entity, action);
        }
        return action;
    }

    @Override
    public void follower(Follower follower) {
        
        try{
            Optional <FollowersEntity> getFollower = followerRepository.findTopByFollowersIdAndChannelIdOrderByFollowedAtDesc(follower.getFollowersId(), follower.getChannelId());
            if(!getFollower.isPresent()){
                // New follower is added
                FollowersEntity entity = new FollowersEntity();
                entity.setFollowersId(follower.getFollowersId());
                entity.setChannelId(follower.getChannelId());
                entity.setAction(followAction.FOLLOW);
                entity.setFollowedAt(LocalDateTime.now());
                followerRepository.save(entity);

                userImp.incrementSubsCount(follower.getChannelId());

            }else{
                // If follower already exists and is followed to the channel it should unfollow 
                // or the entry should be deleted 
                followerRepository.deleteByChannelIdAndFollowersId(follower.getChannelId(), follower.getFollowersId());
                userImp.decrementSubsCount(follower.getChannelId());  
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
