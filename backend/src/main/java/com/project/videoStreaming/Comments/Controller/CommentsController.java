package com.project.videoStreaming.Comments.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.project.videoStreaming.Comments.DTO.Comments;
import com.project.videoStreaming.Comments.Service.CommentServiceImplementation;
import com.project.videoStreaming.SecurityConfig.AuthService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    CommentServiceImplementation Service;

    @Autowired
    AuthService auth;

    @GetMapping("/{videoid}")
    public List<Comments> getComments(@PathVariable UUID videoid) {
        return Service.getComments(videoid);
    }
    
    @PostMapping("/user/comment")
    public void addComments(@RequestBody Comments comment) {
        comment.setUserid(auth.getUserId());
        Service.addComment(comment);
    }
    
    @PostMapping("/user/update")
    public void updateComment (@RequestBody Comments comment){
        comment.setUserid(auth.getUserId());
        Service.updateComment(comment);
    }

    @DeleteMapping("/user/{commentid}/{videoid}")
    public void deleteComment(@PathVariable UUID commentid, @PathVariable UUID videoid){
        Service.deleteComment(commentid, auth.getUserId(), videoid);
    }

}
