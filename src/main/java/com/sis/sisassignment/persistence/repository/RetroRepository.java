package com.sis.sisassignment.persistence.repository;


import com.sis.sisassignment.domain.model.Retrospective;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Notes:
 *   1. It would be a good idea to return cloned Retrospectives from the read methods
 */
@Repository
public class RetroRepository {
    private final Map<String, Retrospective> STORAGE = new HashMap<>();

    public void create(Retrospective retro) {
        this.STORAGE.put(retro.getName(), retro);
    }

    public Retrospective read(String retroName) {
        return this.STORAGE.get(retroName);
    }

    public List<Retrospective> readAll(int offset, int length) {
        Collection<Retrospective> feedbackCollection = this.STORAGE.values();
        if (offset > feedbackCollection.size()) {
            return new ArrayList<>(0);
        }
        return new ArrayList<>(feedbackCollection).subList(offset, Math.min(offset + length, feedbackCollection.size()));
    }

    public List<Retrospective> readAll(int offset, int length, Instant date) {
        return readAll(offset, length).stream()
                .filter(r -> r.getDate().truncatedTo(ChronoUnit.DAYS).equals(date.truncatedTo(ChronoUnit.DAYS)))
                .collect(Collectors.toList());
    }

    /**
     * Method consumer must ensure the retro already exist or a new retro will be created (not ideal).
     */
    public void update(Retrospective retro) {
        this.STORAGE.put(retro.getName(), retro);
    }
}
