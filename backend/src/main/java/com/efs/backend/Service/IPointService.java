package com.efs.backend.Service;

import com.efs.backend.Model.Point;

import java.util.List;

public interface IPointService {

    List<Point> getPoints();

    Point getPointById(Long id);

    void savePoint(Point point);

    void deletePointById(Long id);

    void updatePoint(Point point);

    List<Point> getPointByUserId(Long id);

    Double getSumPoint(Long userId);

    void addParticipationPoints(Long userId);

    void addCreatePoints(Long userId);


}
