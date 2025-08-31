package com.project.videoStreaming.Comments.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.videoStreaming.Comments.DTO.Comments;
import com.project.videoStreaming.Comments.Entity.CommentEntity;
import com.project.videoStreaming.Comments.Repository.CommentRepository;

@Service
public class CommentServiceImplementation implements CommentService{

    @Autowired
    CommentRepository commentRepository;

    @Override
    public List<Comments> getComments(UUID videoid) {
    List<CommentEntity> getComments = commentRepository.findByVideoid(videoid); 

    List<Comments> comments = new ArrayList<>();

    for (CommentEntity entity : getComments) {
        Comments comment = new Comments(); 
        BeanUtils.copyProperties(entity, comment);
        comments.add(comment);
    }

    return comments;
}

    @Override
    public void addComment(Comments comment) {
        
        try{
        CommentEntity entity = new CommentEntity();
        entity.setComment(comment.getComment());
        entity.setUserid(comment.getUserid());
        entity.setVideoid(comment.getVideoid());

        commentRepository.save(entity);
        }catch(Exception e){
            e.printStackTrace(); 
        }
    }

    @Override
    public void updateComment(Comments comment) {
        
        Optional <CommentEntity> getComment = commentRepository.findByUseridAndVideoid(comment.getUserid(), comment.getVideoid());

        if(getComment.isPresent()){
            CommentEntity getCommentEntity = getComment.get();

            if(getCommentEntity.getComment() != null) getCommentEntity.setComment(comment.getComment());
            commentRepository.save(getCommentEntity);
        }
    }

    @Override
    public void deleteComment(UUID commentid, UUID userid, UUID videoid) {
         Optional <CommentEntity> getComment = commentRepository.findByCommentidAndUseridAndVideoid(commentid, userid, videoid);

        if(getComment.isPresent()){
            commentRepository.deleteById(commentid);   
        }
    }


}
