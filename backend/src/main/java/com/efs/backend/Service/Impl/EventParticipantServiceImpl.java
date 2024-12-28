package com.efs.backend.Service.Impl;

import com.efs.backend.DAO.IEventParticipantRepository;
import com.efs.backend.DAO.IEventRepository;
import com.efs.backend.Model.Event;
import com.efs.backend.Model.EventParticipant;
import com.efs.backend.Service.IEventParticipantService;
import com.efs.backend.Service.IPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventParticipantServiceImpl implements IEventParticipantService {

    private IPointService pointService;
    private IEventParticipantRepository eventParticipantRepository;
    private IEventRepository eventRepository;

    @Autowired
    public void setEventParticipantRepository(IEventParticipantRepository eventParticipantRepository, IEventRepository eventRepository, IPointService pointService) {
        this.eventParticipantRepository = eventParticipantRepository;
        this.eventRepository = eventRepository;
        this.pointService = pointService;
    }


    @Override
    public List<EventParticipant> getEventParticipant() {
        return eventParticipantRepository.findAll();
    }

    @Override
    public List<EventParticipant> getEventParticipantByEventId(Long eventId) {
        return eventParticipantRepository.getEventParticipantByEventId(eventId);
    }

    @Override
    public List<EventParticipant> getEventParticipantByUserId(Long userId) {
        return eventParticipantRepository.getEventParticipantByUserId(userId);
    }

    @Override
    public EventParticipant getEventParticipantByEventIdUserId(Long eventId, Long userId) {

        Optional<EventParticipant> result = eventParticipantRepository.getEventParticipantByUserIdByEventId(eventId, userId);

        EventParticipant eventParticipant = null;

        if (result.isPresent()) {
            eventParticipant = result.get();

        } else {
            return null;
        }

        return eventParticipant;
    }

    @Override
    public boolean saveEventParticipant(EventParticipant eventParticipant) {
        System.out.println(isUserParticipatingInEvent(eventParticipant.getEventId(), eventParticipant.getUserId()));
        if (isUserParticipatingInEvent(eventParticipant.getEventId(), eventParticipant.getUserId()) || isEventTimeConflicting(eventParticipant)) {
            return false;
        } else {
            eventParticipantRepository.save(eventParticipant);
            pointService.addParticipationPoints(eventParticipant.getUserId());
            return true;
        }
    }


    @Override
    public void updateEventParticipant(EventParticipant eventParticipant) {
        eventParticipantRepository.save(eventParticipant);
    }

    @Override
    public void deleteEventParticipantByEventIdUserId(Long eventId, Long userId) {

        Optional<EventParticipant> result = eventParticipantRepository.getEventParticipantByUserIdByEventId(eventId, userId);
        result.ifPresent(eventParticipantRepository::delete);
    }

    @Override
    public boolean isUserParticipatingInEvent(Long eventId, Long userId) {
        return eventParticipantRepository.getEventParticipantByUserIdByEventId(userId, eventId).isPresent();
    }

    @Override
    public boolean isEventTimeConflicting(EventParticipant eventParticipant) {

        long userId = eventParticipant.getUserId();
        long eventId = eventParticipant.getEventId();

        Optional<Event> result = eventRepository.findById(eventId);
        if (!result.isPresent()) {
            throw new IllegalArgumentException("Event not found");
        }
        Event event = result.get();

        LocalDateTime newEventStart = event.getStartTime();
        LocalDateTime newEventEnd = event.getEndTime();


        List<EventParticipant> allEventsParticipateByUser = eventParticipantRepository.getEventParticipantByUserId(userId);


        for (EventParticipant existingParticipant : allEventsParticipateByUser) {
            Optional<Event> e = eventRepository.findById(existingParticipant.getEventId());


            if (!e.isPresent()) {
                continue;
            }

            Event existingEvent = e.get();
            LocalDateTime existingEventStart = existingEvent.getStartTime();
            LocalDateTime existingEventEnd = existingEvent.getEndTime();


            if (newEventStart.isBefore(existingEventEnd) && newEventEnd.isAfter(existingEventStart)) {
                return true;
            }
        }


        return false;
    }


    public long countParticipantsByEventId(Long eventId) {
        return eventParticipantRepository.countByEventId(eventId);
    }
}
