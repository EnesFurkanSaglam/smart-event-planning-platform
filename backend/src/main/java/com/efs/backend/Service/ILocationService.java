package com.efs.backend.Service;


import com.efs.backend.Model.Location;

import java.util.List;

public interface ILocationService {


    List<Location> getLocation();

    Location getLocationById(Long id);

    Location saveLocation(Location location);

    void deleteLocationById(Long id);

    void updateLocation(Location id);




}
