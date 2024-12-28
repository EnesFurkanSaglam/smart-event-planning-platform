package com.efs.backend.DAO;

import com.efs.backend.Model.Event;
import com.efs.backend.Model.EventParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IEventRepository extends JpaRepository<Event,Long> {

    @Query(value = "SELECT * FROM events WHERE organizer_id = ?1", nativeQuery = true)
    List<Event> getEventByOrganizerId(Long organizerId);


}
