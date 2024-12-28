package com.efs.backend.DAO;

import com.efs.backend.Model.EventParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface IEventParticipantRepository extends JpaRepository<EventParticipant,Long> {


    @Query(value = "SELECT * FROM event_participants WHERE event_id = ?1", nativeQuery = true)
    List<EventParticipant> getEventParticipantByEventId(Long eventId);

    @Query(value = "SELECT * FROM event_participants WHERE user_id = ?1", nativeQuery = true)
    List<EventParticipant> getEventParticipantByUserId(Long userId);

    @Query(value = "SELECT * FROM event_participants WHERE user_id = ?1 AND event_id = ?2",nativeQuery = true)
    Optional<EventParticipant> getEventParticipantByUserIdByEventId(Long userId, Long eventId);

    @Query(value = "SELECT COUNT(*) FROM event_participants WHERE event_id = ?1", nativeQuery = true)
    long countByEventId(Long eventId);


}
