package com.project.videoStreaming.Comments.DTO;

import java.sql.Timestamp;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comments {
    
    private UUID commentid;
    private String comment;
    private Timestamp commented_at;
    private UUID userid;
    private UUID videoid; 
}
