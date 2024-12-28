package com.efs.backend.DAO;

import com.efs.backend.Model.Comment;
import com.efs.backend.Model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICommentRepository extends JpaRepository<Comment,Long> {

    @Query(value = "SELECT * FROM comments WHERE event_id = ?1", nativeQuery = true)
    List<Comment> getCommentsByEventId(Long eventId);

    @Query(value = "SELECT * FROM comments WHERE message_sender_id = ?1", nativeQuery = true)
    List<Comment> getCommentsByUserId(Long userId);

}
