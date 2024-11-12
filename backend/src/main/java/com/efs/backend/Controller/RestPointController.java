package com.efs.backend.Controller;


import com.efs.backend.Model.Location;
import com.efs.backend.Model.Point;
import com.efs.backend.Service.IPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/point")
public class RestPointController {

    private IPointService pointService;


    @Autowired
    public void  setPointService(IPointService pointService){
        this.pointService = pointService;
    }

    @GetMapping("/points")
    public ResponseEntity<List<Point>> getPoints(){
        List<Point> pointList = pointService.getPoints();
        return ResponseEntity.ok(pointList);
    }
    @GetMapping("points/{id}")
    public ResponseEntity<Point> getPointById(@PathVariable("id") Long id){
        Point point = pointService.getPointById(id);
        if (point != null){
            return ResponseEntity.ok(point);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("points/by-user{id}")
    public ResponseEntity<Point> getPointByUserId(@PathVariable("id") Long userid){
        Point point = pointService.getPointByUserId(userid);
        if (point != null){
            return ResponseEntity.ok(point);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/points")
    public ResponseEntity<?> savePoint(@RequestBody Point point){
        pointService.savePoint(point);
        return ResponseEntity.ok("Point Created");
    }

    @DeleteMapping("/points/{id}")
    public ResponseEntity<?> deletePoint(@PathVariable("id") Long id){
        pointService.deletePointById(id);
        return ResponseEntity.ok("Point deleted...");
    }

    @PutMapping(value = "/points")
    public ResponseEntity<?> updatePoint(@RequestBody Point point) {
        pointService.updatePoint(point);
        return ResponseEntity.ok("Point updated...");
    }




}
