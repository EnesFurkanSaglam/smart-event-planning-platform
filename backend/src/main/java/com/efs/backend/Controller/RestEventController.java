package com.efs.backend.Controller;


import com.efs.backend.Model.Event;
import com.efs.backend.Service.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class RestEventController {

    private IEventService eventService;

    @Autowired
    public void setEventService(IEventService eventService){
        this.eventService = eventService;
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

    @PostMapping("/events")
    public ResponseEntity<?> saveEvent(@RequestBody Event event){
        eventService.saveEvent(event);
        return ResponseEntity.ok("Event Created");
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
