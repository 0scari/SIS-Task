package com.sis.sisassignment.persistence.repository;


import com.sis.sisassignment.domain.model.Retrospective;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class RetroRepository {
    private final Map<String, Retrospective> STORAGE = new HashMap<>();

    public void create(Retrospective retro) {
        this.STORAGE.put(retro.getName(), retro);
    }

    public Optional<Retrospective> read(String retroName) {
        return Optional.ofNullable(this.STORAGE.get(retroName));
    }
}
