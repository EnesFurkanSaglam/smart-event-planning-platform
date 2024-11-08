package com.efs.backend.Service.Impl;

import com.efs.backend.DAO.IEventRepository;
import com.efs.backend.Model.Event;
import com.efs.backend.Service.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class EventServiceImpl implements IEventService {

//    private IEventRepository eventRepository;
//
//    @Autowired
//    public void setEventRepository(IEventRepository eventRepository){
//        this.eventRepository = eventRepository;
//    }
//
//    @Override
//    public List<Event> getEvents() {
//        return eventRepository.findAll();
//    }
//
//    @Override
//    public Event getEventById(Long id) {
//
//        Optional<Event> result = eventRepository.findById(id);
//
//        Event event = null;
//
//        if (result.isPresent()){
//            event = result.get();
//        }else{
//            return null;
//            //excetion fırlatılmasıl lazım
//        }
//
//        return event;
//    }
//
//    @Override
//    public void saveEvent(Event event) {
//        eventRepository.save(event);
//    }
//
//    @Override
//    public void deleteEventById(Long id) {
//        eventRepository.deleteById(id);
//    }
//
//    @Override
//    public void update(Event event) {
//        eventRepository.save(event);
//    }
}
