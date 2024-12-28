package com.efs.backend.Service.Impl;

import com.efs.backend.DAO.ICommentRepository;
import com.efs.backend.DTO.DTOComment;
import com.efs.backend.Model.Comment;
import com.efs.backend.Service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements ICommentService {

    private ICommentRepository commentRepository;


    @Autowired
    public void setCommentRepository(ICommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment getCommentById(Long commentId) {
         Optional <Comment> result = commentRepository.findById(commentId);

         Comment comment = null;

         if (result.isPresent()){
             comment = result.get();
         }else{
             return null;
         }
         return comment;
    }

    @Override
    public List<Comment> getCommentsByEventId(Long eventId) {
        return commentRepository.getCommentsByEventId(eventId);
    }

    @Override
    public List<Comment> getCommentsByUserId(Long userId) {
        return commentRepository.getCommentsByUserId(userId);
    }

    @Override
    public void updateComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);

    }
}
