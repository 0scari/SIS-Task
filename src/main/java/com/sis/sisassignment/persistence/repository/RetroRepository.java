package com.sis.sisassignment.persistence.repository;


import com.sis.sisassignment.domain.model.Retrospective;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RetroRepository {
    private final List<Retrospective> STORAGE = new ArrayList<>();

    public void create(Retrospective retro) {
        this.STORAGE.add(retro);
    }

    public List<Retrospective> readAll() {
        return this.STORAGE;
    }
}
