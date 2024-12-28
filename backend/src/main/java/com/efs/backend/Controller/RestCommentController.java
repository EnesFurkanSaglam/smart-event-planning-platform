package com.efs.backend.Controller;



import com.efs.backend.DTO.DTOComment;
import com.efs.backend.Model.Comment;
import com.efs.backend.Model.Event;
import com.efs.backend.Model.User;
import com.efs.backend.Service.ICommentService;
import com.efs.backend.Service.IEventService;
import com.efs.backend.Service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class RestCommentController {

    private ICommentService commentService;
    private IEventService eventService;
    private IUserService userService;


    @Autowired
    public void setCommentService(ICommentService commentService,IEventService eventService,IUserService userService){
        this.commentService = commentService;
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<DTOComment> getCommentById(@PathVariable("id") Long id){

        Comment dbComment = commentService.getCommentById(id);
        DTOComment dtoComment = new DTOComment();
        BeanUtils.copyProperties(dbComment,dtoComment);
        dtoComment.setEventId(dbComment.getCommentId());
        dtoComment.setMessageSenderId(dbComment.getMessageSender().getUserId());

        return ResponseEntity.ok(dtoComment);

    }

    @GetMapping("/comments/by-event/{id}")
    public ResponseEntity<List<DTOComment>> getCommentsByEventId(@PathVariable("id") Long eventId){

        List<Comment> dbCommentList = commentService.getCommentsByEventId(eventId);
        List<DTOComment> dtoCommentList = new ArrayList<>();

        for (Comment comment : dbCommentList){
            DTOComment dtoComment = new DTOComment();
            BeanUtils.copyProperties(comment,dtoComment);
            dtoComment.setEventId(comment.getCommentId());
            dtoComment.setMessageSenderId(comment.getMessageSender().getUserId());
            dtoCommentList.add(dtoComment);
        }

        return ResponseEntity.ok(dtoCommentList);

    }

    @GetMapping("/comments/by-user/{id}")
    public ResponseEntity<List<DTOComment>> getCommentsByUserId(@PathVariable("id") Long userId){

        List<Comment> dbCommentList = commentService.getCommentsByUserId(userId);
        List<DTOComment> dtoCommentList = new ArrayList<>();

        for (Comment comment : dbCommentList){
            DTOComment dtoComment = new DTOComment();
            BeanUtils.copyProperties(comment,dtoComment);
            dtoComment.setEventId(comment.getCommentId());
            dtoComment.setMessageSenderId(comment.getMessageSender().getUserId());
            dtoCommentList.add(dtoComment);
        }

        return ResponseEntity.ok(dtoCommentList);
    }

    @PostMapping("/comments")
    public ResponseEntity<Comment> saveComment(@RequestBody DTOComment dtoComment){
        Comment commentToSave  = new Comment();

        Event dbEvent = eventService.getEventById(dtoComment.getEventId());
        User dbUser = userService.getUserById(dtoComment.getMessageSenderId());

        BeanUtils.copyProperties(dtoComment,commentToSave);

        commentToSave.setEvent(dbEvent);
        commentToSave.setMessageSender(dbUser);

        Comment comment = commentService.saveComment(commentToSave);

        return ResponseEntity.ok(comment);

    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long id){
        commentService.deleteComment(id);
        return ResponseEntity.ok("Comment Deleted");
    }

    @PutMapping("/comments")
    public ResponseEntity<?> updateComment(@RequestBody Comment comment ){
        commentService.updateComment(comment);
        return ResponseEntity.ok("Comment updated");
    }



}
