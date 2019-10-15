package com.codegym.service.impl;

import com.codegym.model.Location;
import com.codegym.repository.LocationRepository;
import com.codegym.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

public class LocationServiceImpl extends LocationService {
    @Autowired
    private LocationRepository locationRepository;

    @Override
    protected CrudRepository<Location, Long> repository() {
        return locationRepository;
    }
}
