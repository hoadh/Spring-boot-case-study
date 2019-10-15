package com.codegym.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Squad {
    private Map<Long, FootballPlayer> footballPlayerMap = new HashMap<>();

    public List<FootballPlayer> getSquad() {
        return new ArrayList<>(footballPlayerMap.values());
    }

    public void addSquad(FootballPlayer footballPlayer) {
        footballPlayerMap.put(footballPlayer.getId(),footballPlayer);
    }

    public int getSize(){
        return footballPlayerMap.size();
    }

    public boolean contain(long id){
        return footballPlayerMap.containsKey(id);
    }

    public void remove(long id){
        footballPlayerMap.remove(id);

    }

}
