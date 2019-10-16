package com.codegym.service.impl;

import com.codegym.model.FootballPlayer;
import com.codegym.repository.FootballRepository;
import com.codegym.service.FootballPlayerServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class FootballPlayerServiceImplInterface implements FootballPlayerServiceInterface {
    @Autowired
    private FootballRepository footballRepository;

    @Override
    public List<FootballPlayer> findAll() {
        return (List<FootballPlayer>) footballRepository.findAll();
    }

    @Override
    public Page<FootballPlayer> findAll(Pageable pageable) {
        return footballRepository.findAll(pageable);
    }

    @Override
    public Iterable<FootballPlayer> search(String keyword) {
        return footballRepository.findAllByFirstNameContains(keyword);
    }

    @Override
    public Page<FootballPlayer> search(String keyword, Pageable pageable) {
        return footballRepository.findAllByFirstNameContains(keyword,pageable);
    }

    @Override
    public FootballPlayer findOne(Long id) {
        return footballRepository.findById(id).get();
    }

    @Override
    public FootballPlayer save(FootballPlayer footballPlayer) {
        return footballRepository.save(footballPlayer);
    }

    @Override
    public List<FootballPlayer> save(List<FootballPlayer> footballPlayers) {
        return (List<FootballPlayer>) footballRepository.saveAll(footballPlayers);
    }

    @Override
    public boolean exists(Long id) {
        return footballRepository.existsById(id);
    }

    @Override
    public List<FootballPlayer> findAll(List<Long> ids) {
        return (List<FootballPlayer>) footballRepository.findAllById(ids);
    }

    @Override
    public Long count() {
        return footballRepository.count();
    }

    @Override
    public void delete(Long id) {
        footballRepository.deleteById(id);
    }

    @Override
    public void delete(FootballPlayer footballPlayer) {
        footballRepository.delete(footballPlayer);
    }

    @Override
    public void deleteAll() {
        footballRepository.deleteAll();
    }
}
