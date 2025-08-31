package com.project.videoStreaming.Users.Repository;
import com.project.videoStreaming.Users.Entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmailAndPassword(String email, String password);

    Optional<UserEntity> findByEmail(String email);

    @Query(value = "SELECT userid from users where email =:email", nativeQuery = true)
    UUID findUserIdByEmail(@Param("email") String email);
    
    Optional<UserEntity>findByUsername(String username);

}
