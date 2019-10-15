package com.codegym.service.impl;


import com.codegym.model.FootballPlayer;
import com.codegym.repository.FootballRepository;
import com.codegym.service.FootballPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.stream.Collectors;

public class FootballPlayerServiceImpl extends FootballPlayerService {
    @Autowired
    private FootballRepository footballRepository;

    @Override
    public Page<FootballPlayer> findAll(Pageable pageInfo) {
        return footballRepository.findAll(pageInfo);
    }

    @Override
    public List<FootballPlayer> search(String keyword) {
        Iterable<FootballPlayer> searchResult = footballRepository
                .findAllByFirstNameContains( keyword);
        return streamAll(searchResult).collect(Collectors.toList());
    }

    @Override
    public Page<FootballPlayer> search(String keyword, Pageable pageInfo) {
        return footballRepository
                .findAllByFirstNameContains(keyword, pageInfo);
    }

    @Override
    protected CrudRepository<FootballPlayer, Long> repository() {
        return footballRepository;
    }
}
