package com.project.videoStreaming.Followers.Service;

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
                FollowersEntity entity = new FollowersEntity();
                BeanUtils.copyProperties(getFollower, entity);
                followerRepository.save(entity);

                userImp.incrementSubsCount(follower.getChannelId());

            }else if (getFollower.isPresent()){
                FollowersEntity entity = getFollower.get();
                if(entity.getAction() == followAction.FOLLOW && follower.getAction() == followAction.UNFOLLOW){
                    entity.setAction(followAction.UNFOLLOW);
                    followerRepository.save(entity);

                    userImp.decrementSubsCount(follower.getChannelId());
                    
                }else if(entity.getAction() == followAction.UNFOLLOW && follower.getAction() == followAction.FOLLOW){
                    entity.setAction(followAction.FOLLOW);
                    followerRepository.save(entity);

                    userImp.incrementSubsCount(follower.getChannelId());
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
