package com.efs.backend.Service;


import com.efs.backend.Model.EventParticipant;


import java.util.List;


public interface IEventParticipantService {

    List<EventParticipant> getEventParticipant();

    List<EventParticipant> getEventParticipantByEventId(Long eventId);

    List<EventParticipant> getEventParticipantByUserId(Long userId);

    EventParticipant getEventParticipantByEventIdUserId(Long EventId,Long UserId);

    boolean saveEventParticipant(EventParticipant eventParticipant);

    void updateEventParticipant(EventParticipant eventParticipant);

    void deleteEventParticipantByEventIdUserId(Long eventId, Long userId);

    boolean isUserParticipatingInEvent(Long eventId, Long userId);

    boolean isEventTimeConflicting(EventParticipant eventParticipant);
}


