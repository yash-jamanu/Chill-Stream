package com.project.videoStreaming.Wishlist.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.videoStreaming.Wishlist.Entity.WishlistEntity;

@Repository
public interface WishlistRepository extends JpaRepository<WishlistEntity, UUID> {

    @Query (value = "SELECT videoid FROM Wishlist WHERE userid = :userid", nativeQuery = true)
    List<UUID> findVideoidByUserid(@Param ("userid") UUID userid);
}
