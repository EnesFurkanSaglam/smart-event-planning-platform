package com.efs.backend.Service.Impl;

import com.efs.backend.DAO.IEventParticipantRepository;
import com.efs.backend.DAO.IEventRepository;
import com.efs.backend.DAO.IPointRepository;
import com.efs.backend.DAO.IUserRepository;
import com.efs.backend.Model.Event;
import com.efs.backend.Model.EventParticipant;
import com.efs.backend.Model.Point;
import com.efs.backend.Model.User;
import com.efs.backend.Service.IPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PointServiceImpl implements IPointService {

    private IPointRepository pointRepository;
    private IUserRepository userRepository;
    private IEventParticipantRepository eventParticipantRepository;
    private IEventRepository eventRepository;



    @Autowired
    public void setPointRepository(IPointRepository pointRepository, IUserRepository userRepository, IEventParticipantRepository eventParticipantRepository,IEventRepository eventRepository){
        this.pointRepository = pointRepository;
        this.userRepository = userRepository;
        this.eventParticipantRepository = eventParticipantRepository;
        this.eventRepository = eventRepository;

    }


    @Override
    public List<Point> getPoints() {
        return pointRepository.findAll();
    }


    @Override
    public Point getPointById(Long id) {
        Optional <Point> result = pointRepository.findById(id);

        Point point = null;
        if (result.isPresent()){
            point = result.get();
        }else {
            return null;
        }
        return point;
    }


    @Override
    public void savePoint(Point point) {
        pointRepository.save(point);
    }

    @Override
    public void deletePointById(Long id) {
        pointRepository.deleteById(id);
    }

    @Override
    public void updatePoint(Point point) {
        pointRepository.save(point);
    }

    @Override
    public List<Point> getPointByUserId(Long id) {

        Optional<List<Point>> optionalPoint = pointRepository.getByUserId(id);
        List<Point> pointList = null;

        if (optionalPoint.isPresent()){
            pointList = optionalPoint.get();
        }else {
            return null;
        }

        return pointList;

    }


    @Override
    public void addParticipationPoints(Long userId) {

        double participationPoints = 10;
        String points = String.valueOf(participationPoints);

        Optional<User> optionalUser = userRepository.findById(userId);
        User user = null;
        if (optionalUser.isPresent()){
            user = optionalUser.get();
        }else {
            return;
        }

        Point point = new Point();
        point.setPoint(points);
        point.setUser(user);

        pointRepository.save(point);

    }

    @Override
    public void addCreatePoints(Long userId) {

        double createPoint = 15;
        String points = String.valueOf(createPoint);

        Optional<User> optionalUser = userRepository.findById(userId);
        User user = null;
        if (optionalUser.isPresent()){
            user = optionalUser.get();
        }else {
            return;
        }

        Point point = new Point();
        point.setPoint(points);
        point.setUser(user);

        pointRepository.save(point);

    }

    @Override
    public Double getSumPoint(Long userId) {

        double sumPoint = 0.0;

        Optional<User> optionalUser = userRepository.findById(userId);
        User user = null;
        if (optionalUser.isPresent()){
            user = optionalUser.get();
        }else {
            return 0.0;
        }

        Optional<List<Point>> optionalPoint = pointRepository.getByUserId(userId);
        List<Point> pointList = null;

        if (optionalPoint.isPresent()){
            pointList = optionalPoint.get();
        }else {
            return 0.0;
        }

        for (Point point : pointList){
            double points = Double.parseDouble(point.getPoint());
            sumPoint += points;
        }


        List<EventParticipant> eventParticipantList = eventParticipantRepository.getEventParticipantByUserId(userId);
        List<Event> eventParticipantByUser = new ArrayList<>();
        for (EventParticipant eventParticipant : eventParticipantList){
            if (eventParticipant.getUserId().equals(userId)){
                Event event = eventRepository.findById(eventParticipant.getEventId()).get();
                eventParticipantByUser.add(event);
            }
        }


        if (!eventParticipantByUser.isEmpty()){
            return sumPoint+20;
        }
        else {
            return  sumPoint;
        }

    }

}
