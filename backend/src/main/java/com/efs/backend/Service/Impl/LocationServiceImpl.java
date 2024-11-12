package com.efs.backend.Service.Impl;

import com.efs.backend.DAO.ILocationRepository;
import com.efs.backend.Model.Location;
import com.efs.backend.Service.ILocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class LocationServiceImpl  implements ILocationService {

    private ILocationRepository locationRepository;

    @Autowired
    public void setLocationRepository(ILocationRepository locationRepository){
        this.locationRepository = locationRepository;
    }



    @Override
    public List<Location> getLocation() {
        return locationRepository.findAll();
    }

    @Override
    public Location getLocationById(Long id) {
        Optional<Location> result = locationRepository.findById(id);

        Location location = null;
        if (result.isPresent()){
            location = result.get();
        }
        else {
            return null;
        }
        return location;
    }

    @Override
    public void saveLocation(Location location) {
        locationRepository.save(location);

    }

    @Override
    public void deleteLocationById(Long id) {
        locationRepository.deleteById(id);
    }

    @Override
    public void updateLocation(Location location) {
        locationRepository.save(location);
    }

}
