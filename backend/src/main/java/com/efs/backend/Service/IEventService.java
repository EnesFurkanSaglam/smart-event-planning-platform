package com.efs.backend.Service;


import com.efs.backend.Model.Event;

import java.util.List;


public interface IEventService {

    List<Event> getEvents();
    Event getEventById(Long id);
    void saveEvent(Event event);
    void deleteEventById(Long id);
    void update(Event event);

}
