package com.project.videoStreaming.Comments.Service;

import java.util.List;
import java.util.UUID;

import com.project.videoStreaming.Comments.DTO.Comments;

public interface CommentService {
    public List<Comments> getComments(UUID videoid);

    public void addComment(Comments comment);

    public void updateComment (Comments comment);

    public void deleteComment(UUID commentid, UUID userid, UUID videoid);

    
}
