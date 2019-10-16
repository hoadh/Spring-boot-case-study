package com.codegym.service.impl;

import com.codegym.model.Location;
import com.codegym.repository.LocationRepository;
import com.codegym.service.LocationServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class LocationServiceImplInterface implements LocationServiceInterface {
    @Autowired
    private LocationRepository locationRepository;

    @Override
    public List<Location> findAll() {
        return (List<Location>) locationRepository.findAll();
    }

    @Override
    public Page<Location> findAll(Pageable pageable) {
        return locationRepository.findAll(pageable);
    }

    @Override
    public Location findOne(Long id) {
        return locationRepository.findById(id).get();
    }

    @Override
    public Location save(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public List<Location> save(List<Location> locationList) {
        return (List<Location>) locationRepository.saveAll(locationList);
    }

    @Override
    public boolean exists(Long id) {
        return locationRepository.existsById(id);
    }

    @Override
    public List<Location> findAll(List<Long> ids) {
        return (List<Location>) locationRepository.findAllById(ids);
    }

    @Override
    public Long count() {
        return locationRepository.count();
    }

    @Override
    public void delete(Long id) {
        locationRepository.deleteById(id);
    }

    @Override
    public void delete(Location location) {
        locationRepository.delete(location);
    }

    @Override
    public void deleteAll() {
        locationRepository.deleteAll();
    }
}
