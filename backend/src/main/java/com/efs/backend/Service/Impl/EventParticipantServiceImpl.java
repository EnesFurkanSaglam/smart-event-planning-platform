package com.efs.backend.Service.Impl;

import com.efs.backend.DAO.IEventParticipantRepository;
import com.efs.backend.Model.EventParticipant;
import com.efs.backend.Service.IEventParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventParticipantServiceImpl implements IEventParticipantService {


    private IEventParticipantRepository eventParticipantRepository;

    @Autowired
    public void setEventParticipantRepository(IEventParticipantRepository eventParticipantRepository){
        this.eventParticipantRepository = eventParticipantRepository;
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

        Optional<EventParticipant> result = eventParticipantRepository.getEventParticipantByUserIdByEventId(eventId,userId);

        EventParticipant eventParticipant = null;

        if (result.isPresent()){
            eventParticipant = result.get();

        }else {
            return null;
            //excetion fırlatılmasıl lazım
        }

        return eventParticipant;
    }

    @Override
    public void saveEventParticipant(EventParticipant eventParticipant) {
        eventParticipantRepository.save(eventParticipant);
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

    public boolean isUserParticipatingInEvent(Long eventId, Long userId) {

        return eventParticipantRepository.getEventParticipantByUserIdByEventId(eventId, userId).isPresent();
    }

    public long countParticipantsByEventId(Long eventId) {

        return eventParticipantRepository.countByEventId(eventId);
    }
}
