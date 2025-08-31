package com.project.videoStreaming.Users.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.videoStreaming.Users.Entity.UserEntity;

@Repository
public interface UserLoginRepository extends JpaRepository<UserEntity, UUID> {
    UserEntity findByEmail(String email);
}
