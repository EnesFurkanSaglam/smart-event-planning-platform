package com.efs.backend.Service.Impl;

import com.efs.backend.DAO.IPointRepository;
import com.efs.backend.Model.Point;
import com.efs.backend.Service.IPointService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class PointServiceImpl implements IPointService {

    private IPointRepository pointRepository;


    @Autowired
    public void setPointRepository(IPointRepository pointRepository){
        this.pointRepository = pointRepository;
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
            //excetion fırlatılmasıl lazım
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
    public Point getPointByUserId(Long id) {
        Optional <Point> result = pointRepository.getByUserId(id);

        Point point = null;
        if (result.isPresent()){
            point = result.get();
        }else {
            return null;
            //excetion fırlatılmasıl lazım
        }
        return point;
    }
}
