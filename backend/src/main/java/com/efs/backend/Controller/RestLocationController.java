package com.efs.backend.Controller;


import com.efs.backend.Model.Location;
import com.efs.backend.Service.ILocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
public class RestLocationController {

    private ILocationService locationService;

    @Autowired
    public void setLocationService(ILocationService locationService){
        this.locationService = locationService;
    }

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getLocations(){
        List<Location> locationList = locationService.getLocation();
        return ResponseEntity.ok(locationList);
    }

    @GetMapping("/locations/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable("id") Long id){
        Location location = locationService.getLocationById(id);
        if (location != null){
            return ResponseEntity.ok(location);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/locations")
    public ResponseEntity<?> saveEvent(@RequestBody Location location){
        locationService.saveLocation(location);
        return ResponseEntity.ok("Location Created");
    }

    @DeleteMapping("/locations/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") Long id){
        locationService.deleteLocationById(id);
        return ResponseEntity.ok("Location deleted...");
    }

    @PutMapping(value = "/locations")
    public ResponseEntity<?> updateEvent(@RequestBody Location location) {
        locationService.updateLocation(location);
        return ResponseEntity.ok("Location updated...");
    }


}
