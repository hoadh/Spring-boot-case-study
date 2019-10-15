package com.codegym.repository;

import com.codegym.model.FootballPlayer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FootballRepository extends PagingAndSortingRepository<FootballPlayer,Long> {
    Iterable<FootballPlayer> findAllByFirstNameContains(String firstNumber);

    Page<FootballPlayer> findAllByFirstNameContains( String firstName, Pageable pageable);
}
