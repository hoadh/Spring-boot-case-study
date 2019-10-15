package com.codegym.repository;

import com.codegym.model.Location;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LocationRepository extends PagingAndSortingRepository<Location,Long> {
}
