package com.efs.backend.Controller;


import com.efs.backend.Model.EventParticipant;
import com.efs.backend.Model.Point;
import com.efs.backend.Service.IEventParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventParticipant")
public class RestEventParticipantController {

    private IEventParticipantService eventParticipantService;

    @Autowired
    public void setEventParticipantService(IEventParticipantService eventParticipantService){
        this.eventParticipantService = eventParticipantService;
    }

    @GetMapping("/eventParticipants")
    public ResponseEntity<List<EventParticipant>> getEventParticipant(){
        List<EventParticipant> eventParticipantList = eventParticipantService.getEventParticipant();
        return ResponseEntity.ok(eventParticipantList);
    }

    @GetMapping("/eventParticipants/by-event/{id}")
    public ResponseEntity<List<EventParticipant>> getEventParticipantByEventId(@PathVariable ("id") Long id){
        List<EventParticipant> eventParticipantList = eventParticipantService.getEventParticipantByEventId(id);
        if (eventParticipantList != null) {
            return ResponseEntity.ok(eventParticipantList);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/eventParticipants/by-user/{id}")
    public ResponseEntity<List<EventParticipant>> getEventParticipantByUserId(@PathVariable ("id") Long id){
        List<EventParticipant> eventParticipantList = eventParticipantService.getEventParticipantByUserId(id);
        if (eventParticipantList != null) {
            return ResponseEntity.ok(eventParticipantList);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/eventParticipants/by-user/{userId}/by-event/{eventId}")
    public ResponseEntity<EventParticipant> getEventParticipantByEventIdUserId(
            @PathVariable ("userId") Long userId,
            @PathVariable("eventId") Long eventId){
        EventParticipant eventParticipant = eventParticipantService.getEventParticipantByEventIdUserId(eventId,userId);
        if (eventParticipant != null) {
            return ResponseEntity.ok(eventParticipant);
        }else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/eventParticipants")
    public ResponseEntity<?> saveEventParticipants(@RequestBody EventParticipant eventParticipant){
        eventParticipantService.saveEventParticipant(eventParticipant);
        return ResponseEntity.ok("Event Participants created");
    }

    @DeleteMapping("/eventParticipants/user/{userId}/event/eventId")
    public ResponseEntity<?> deleteEventParticipants(
            @PathVariable("userId") Long userId,
            @PathVariable("eventId") Long eventId){

        eventParticipantService.deleteEventParticipantByEventIdUserId(eventId,userId);
        return ResponseEntity.ok("Event Participant deleted...");
    }

    @PutMapping(value = "/eventParticipants")
    public ResponseEntity<?> updateEventParticipants(@RequestBody EventParticipant eventParticipant) {

        eventParticipantService.updateEventParticipant(eventParticipant);
        return ResponseEntity.ok("Event Participant updated...");
    }






}
