package com.project.videoStreaming.Wishlist.DTO;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wishlist {

    private UUID id;
    private UUID userid;
    private UUID videoid;
}
