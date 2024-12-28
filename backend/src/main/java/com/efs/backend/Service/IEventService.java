package com.efs.backend.Service;


import com.efs.backend.Model.Event;

import java.util.List;


public interface IEventService {

    List<Event> getEvents();

    Event getEventById(Long id);

    Event saveEvent(Event event);

    void deleteEventById(Long id);

    void update(Event event);

    List<Event> getEventsByOrganizerId(Long organizerId);

    List<Event> getEventByUserId(Long userId);

    List<Event> suggestEventByUserId(Long userId);

}
