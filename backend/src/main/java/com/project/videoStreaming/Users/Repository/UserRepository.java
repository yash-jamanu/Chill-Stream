package com.project.videoStreaming.Users.Repository;
import com.project.videoStreaming.Users.Entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmailAndPassword(String email, String password);

    Optional<UserEntity> findByUserId(UUID UserId);

    Optional<UserEntity> findByEmail(String email);
    
    Optional<UserEntity>findByUsername(String username);

}
