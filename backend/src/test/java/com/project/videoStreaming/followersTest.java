package com.project.videoStreaming;

import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.videoStreaming.Followers.DTO.followAction;
import com.project.videoStreaming.Followers.Entity.FollowersEntity;
import com.project.videoStreaming.Followers.Repository.FollowerRepository;

@SpringBootTest
public class followersTest {

    @Autowired
    private FollowerRepository followerRepository;

    @Test
    public void testRepository(){

        String id1 = "c197465c-9b44-4906-859f-9132dbfd44a0";

        String id2= "c197465c-9b44-4906-859f-9132dbfd44a0";

        Optional <FollowersEntity> getFollower = followerRepository.findTopByFollowersIdAndChannelIdOrderByFollowedAtDesc(UUID.fromString(id1), UUID.fromString(id2));

        FollowersEntity entity = getFollower.get();
        Assertions.assertThat(entity.getFollowersId()).isEqualTo(UUID.fromString("c197465c-9b44-4906-859f-9132dbfd44a0"));
        Assertions.assertThat(entity.getChannelId()).isEqualTo(UUID.fromString("c197465c-9b44-4906-859f-9132dbfd44a0"));
        Assertions.assertThat(entity.getAction()).isEqualTo(followAction.FOLLOW);
    }
}
