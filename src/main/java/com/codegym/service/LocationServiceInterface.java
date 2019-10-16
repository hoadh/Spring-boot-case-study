package com.codegym.service;

import com.codegym.model.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LocationServiceInterface {

    List<Location> findAll();

    Page<Location> findAll(Pageable pageable);

    Location findOne(Long id);

    Location save(Location location);

    List<Location> save(List<Location> locationList);

    boolean exists(Long id);

    List<Location> findAll(List<Long> ids);

    Long count();

    void delete(Long id);

    void delete(Location location);

    void deleteAll();
}
