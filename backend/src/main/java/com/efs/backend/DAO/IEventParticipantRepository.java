package com.efs.backend.DAO;

import com.efs.backend.Model.EventParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEventParticipantRepository extends JpaRepository<EventParticipant,Long> {
}
