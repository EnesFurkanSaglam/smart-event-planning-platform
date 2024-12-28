package com.efs.backend.Controller;


import com.efs.backend.Model.Location;
import com.efs.backend.Model.User;
import com.efs.backend.Service.ILocationService;
import com.efs.backend.Service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
public class RestLocationController {

    private ILocationService locationService;
    private IUserService userService;

    @Autowired
    public void setLocationService(ILocationService locationService,IUserService userService){
        this.locationService = locationService;
        this.userService = userService;
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

    @PostMapping("/locations/with-user/{id}")
    public ResponseEntity<Location> saveLocationWithUserId(@RequestBody Location location,@PathVariable("id") Long userId){
        locationService.saveLocation(location);
        User user = userService.getUserById(userId);
        user.setLocation(location);
        userService.updateUser(user);
        return ResponseEntity.ok(location);
    }

    @DeleteMapping("/locations/{id}")
    public ResponseEntity<String> deleteLocation(@PathVariable("id") Long id){
        locationService.deleteLocationById(id);
        return ResponseEntity.ok("location deleted");
    }



    @PutMapping(value = "/locations")
    public ResponseEntity<String> updateLocation(@RequestBody Location location) {
        locationService.updateLocation(location);
        return ResponseEntity.ok("Location updated...");
    }


}
