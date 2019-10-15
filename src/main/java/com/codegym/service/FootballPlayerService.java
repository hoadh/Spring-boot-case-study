package com.codegym.service;


import com.codegym.model.FootballPlayer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public abstract class FootballPlayerService extends AbstractEntityServiceImpl<FootballPlayer, Long> {
    public abstract Page<FootballPlayer> findAll(Pageable pageInfo);

    public abstract List<FootballPlayer> search(String keyword);

    public abstract Page<FootballPlayer> search(String keyword, Pageable pageInfo);
}
