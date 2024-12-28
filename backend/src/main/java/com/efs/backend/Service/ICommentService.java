package com.efs.backend.Service;



import com.efs.backend.DTO.DTOComment;
import com.efs.backend.Model.Comment;

import java.util.List;

public interface ICommentService {

    Comment getCommentById(Long commentId);

    List<Comment> getCommentsByEventId(Long eventId);

    List<Comment> getCommentsByUserId(Long userId);

    Comment saveComment(Comment comment);

    void updateComment(Comment comment);

    void deleteComment(Long commentId);

}

