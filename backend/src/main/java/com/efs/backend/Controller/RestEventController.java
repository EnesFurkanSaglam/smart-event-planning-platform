package com.efs.backend.Controller;


import com.efs.backend.DTO.DTOEvent;
import com.efs.backend.DTO.DTOEventToSave;
import com.efs.backend.Model.Event;
import com.efs.backend.Model.EventParticipant;
import com.efs.backend.Model.Location;
import com.efs.backend.Model.User;
import com.efs.backend.Service.IEventParticipantService;
import com.efs.backend.Service.IEventService;
import com.efs.backend.Service.ILocationService;
import com.efs.backend.Service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/event")
public class RestEventController {

    private IEventService eventService;
    private IUserService userService;
    private ILocationService locationService;
    private IEventParticipantService eventParticipantService;

    @Autowired
    public void setEventService(IEventService eventService,IUserService userService,ILocationService locationService,IEventParticipantService eventParticipantService){
        this.eventParticipantService = eventParticipantService;
        this.eventService = eventService;
        this.locationService = locationService;
        this.userService = userService;
    }

    @GetMapping("/events")
    public ResponseEntity<List<Event>> getEvents(){
        List<Event> eventList = eventService.getEvents();
        return ResponseEntity.ok(eventList);
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable("id") Long id){
        Event event = eventService.getEventById(id);
        if (event != null){
            return ResponseEntity.ok(event);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/events/by-organizer/{id}")
    public ResponseEntity<List<DTOEvent>> getEventByOrganizerId(@PathVariable("id") Long id){

        List<Event> eventList = eventService.getEventsByOrganizerId(id);
        List<DTOEvent> dtoEventList = new ArrayList<>();
        for (Event event : eventList) {
            DTOEvent dtoEvent = new DTOEvent();
            BeanUtils.copyProperties(event,dtoEvent);
            dtoEventList.add(dtoEvent);
        }
        return ResponseEntity.ok(dtoEventList);
    }


    @GetMapping("/events/by-user/{id}")
    public ResponseEntity<List<Event>> getEventByUserId(@PathVariable("id") Long id){

        List<Event> eventList = eventService.getEventByUserId(id);
        List<DTOEvent> dtoEventList = new ArrayList<>();
        for (Event event : eventList) {
            DTOEvent dtoEvent = new DTOEvent();
            BeanUtils.copyProperties(event,dtoEvent);

            dtoEventList.add(dtoEvent);
        }
        return ResponseEntity.ok(eventList);

    }


    @GetMapping("/events/suggest/by-user/{id}")
    public ResponseEntity<List<Event>> getSuggestedEventBuUserId(@PathVariable("id")Long userId){

        List<Event> eventList = eventService.suggestEventByUserId(userId);
        return ResponseEntity.ok(eventList);
    }


    @PostMapping("/events")
    public ResponseEntity<Event> saveEvent(@RequestBody DTOEventToSave dtoEventToSave) {

        Event eventToSave = new Event();

        User dbUser = userService.getUserById(dtoEventToSave.getOrganizerId());

        Location newLocation = dtoEventToSave.getLocation();
        Location dbLocation = locationService.saveLocation(newLocation);


        BeanUtils.copyProperties(dtoEventToSave, eventToSave);
        eventToSave.setOrganizer(dbUser);
        eventToSave.setLocation(dbLocation);

        Event savedEvent = eventService.saveEvent(eventToSave);

        return ResponseEntity.ok(savedEvent);
    }


    @DeleteMapping("/events/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") Long id){
        eventService.deleteEventById(id);
        return ResponseEntity.ok("Event deleted...");
    }

    @PutMapping(value = "/events")
    public ResponseEntity<?> updateEvent(@RequestBody Event event) {
        eventService.update(event);
        return ResponseEntity.ok("Event updated...");
    }

}
