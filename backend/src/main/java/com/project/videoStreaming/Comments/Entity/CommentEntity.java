package com.project.videoStreaming.Comments.Entity;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "commentid", nullable = false)
    private UUID commentid;

    @Column(name = "videoid")
    private UUID videoid;

    @Column(name = "userid")
    private UUID userid;

    @Column (name = "comment")
    private String comment;

    @Column(name = "commented_at", insertable = false, updatable = false)
    private Timestamp commented_at;
}
