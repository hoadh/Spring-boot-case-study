package com.codegym.service;

import com.codegym.model.FootballPlayer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FootballPlayerServiceInterface {
    List<FootballPlayer> findAll();

    Page<FootballPlayer> findAll(Pageable pageable);

    Iterable<FootballPlayer> search(String keyword);

    Page<FootballPlayer> search(String keyword, Pageable pageable);

    FootballPlayer findOne(Long id);

    FootballPlayer save(FootballPlayer footballPlayer);

    List<FootballPlayer> save(List<FootballPlayer> personList);

    boolean exists(Long id);

    List<FootballPlayer> findAll(List<Long> ids);

    Long count();

    void delete(Long id);

    void delete(FootballPlayer footballPlayer);

    void deleteAll();

}
