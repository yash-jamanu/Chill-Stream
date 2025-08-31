package com.project.videoStreaming.Followers.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.videoStreaming.Followers.DTO.Follower;
import com.project.videoStreaming.Followers.Entity.FollowersEntity;
import com.project.videoStreaming.Followers.Repository.FollowerRepository;
import com.project.videoStreaming.Users.Entity.UserEntity;
import com.project.videoStreaming.Users.Repository.UserRepository;

@Service
public class FollowersServiceImplementation implements FollowerService {

    @Autowired
    FollowerRepository followerRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void follower(Follower follower) {
        
        try{
            if (!followerRepository.existsByChannelIdAndFollowersId(follower.getChannelId(), follower.getFollowersId())) {
                FollowersEntity entity = new FollowersEntity();
                entity.setChannelId(follower.getChannelId());
                entity.setFollowersId(follower.getFollowersId());
                followerRepository.save(entity);

                Optional<UserEntity> userEntity = userRepository.findById(entity.getChannelId());
                if (userEntity.isPresent()) {
                    UserEntity entity1 = userEntity.get();
                    entity1.setSubs_count(entity1.getSubs_count() + 1);
                    userRepository.save(entity1);
                }
            }else{
                Optional <FollowersEntity> getFollower = followerRepository.findByChannelIdAndFollowersId(follower.getChannelId(), follower.getFollowersId());
                if(getFollower.isPresent()){
                    FollowersEntity getFollowersEntity = getFollower.get();
                    followerRepository.deleteById(getFollowersEntity.getId());

                    Optional<UserEntity> userEntity = userRepository.findById(follower.getChannelId());
                    if (userEntity.isPresent()) {
                        UserEntity entity1 = userEntity.get();
                        entity1.setSubs_count(entity1.getSubs_count() - 1);
                        userRepository.save(entity1);
                    }
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
