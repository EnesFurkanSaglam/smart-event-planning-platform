package com.efs.backend.Service.Impl;

import com.efs.backend.DAO.IEventParticipantRepository;
import com.efs.backend.DAO.IEventRepository;
import com.efs.backend.DAO.IUserRepository;
import com.efs.backend.Model.Event;
import com.efs.backend.Model.EventParticipant;
import com.efs.backend.Model.User;
import com.efs.backend.Service.IEventService;
import com.efs.backend.Service.IPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class EventServiceImpl implements IEventService {

    private IEventRepository eventRepository;
    private IEventParticipantRepository eventParticipantRepository;
    private IUserRepository userRepository;
    private IPointService pointService;

    @Autowired
    public void setEventRepository(IEventRepository eventRepository,IEventParticipantRepository eventParticipantRepository,IUserRepository userRepository,IPointService pointService){
        this.eventRepository = eventRepository;
        this.eventParticipantRepository = eventParticipantRepository;
        this.userRepository = userRepository;
        this.pointService = pointService;
    }

    @Override
    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event getEventById(Long id) {

        Optional<Event> result = eventRepository.findById(id);

        Event event = null;

        if (result.isPresent()){
            event = result.get();
        }else{
            return null;
        }

        return event;
    }

    @Override
    public Event saveEvent(Event event) {
        pointService.addCreatePoints(event.getOrganizer().getUserId());
        return eventRepository.save(event);
    }

    @Override
    public void deleteEventById(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public void update(Event event) {
        eventRepository.save(event);
    }

    @Override
    public List<Event> getEventsByOrganizerId(Long organizerId) {
        return eventRepository.getEventByOrganizerId(organizerId);
    }

    @Override
    public List<Event> getEventByUserId(Long userId) {

        List<EventParticipant> eventParticipantList = eventParticipantRepository.getEventParticipantByUserId(userId);
        List<Event> eventList = eventRepository.findAll();
        List<Event> eventListUser = new ArrayList<>();

        for (Event event : eventList){
            for (EventParticipant eventParticipant :eventParticipantList){
                if (event.getEventId().equals(eventParticipant.getEventId())){
                    eventListUser.add(event);
                }
            }

        }

        return eventListUser;
    }

    @Override
    public List<Event> suggestEventByUserId(Long userId) {

        List<EventParticipant> eventParticipantList = eventParticipantRepository.getEventParticipantByUserId(userId);

        List<Event> eventList = eventRepository.findAll();

        Set<String> categorySet = new HashSet<>();

        for (EventParticipant eventParticipant : eventParticipantList) {
            for (Event event : eventList) {
                if (event.getEventId().equals(eventParticipant.getEventId())) {
                    categorySet.add(event.getCategory());
                }
            }
        }

        String finalCategory = String.join(", ", categorySet);

        Optional<User> result = userRepository.findById(userId);
        if (result.isEmpty()) {
           return null;
        }
        User user = result.get();
        String userInterest = user.getInterest() + " ";


        String finalInterest = userInterest;
        if (!categorySet.isEmpty()) {
            finalInterest += ", " + String.join(", ", categorySet);
        }

        List<Event> events = new ArrayList<>();
        for (Event event : eventList) {
            if (finalInterest.contains(event.getCategory())) {
                events.add(event);
            }
        }

        return events;
    }

}
