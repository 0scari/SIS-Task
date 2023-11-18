package com.sis.sisassignment.domain.service;

import com.sis.sisassignment.domain.model.Retrospective;
import com.sis.sisassignment.persistence.repository.RetroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetroService {

    private final RetroRepository repo;

    public void save(Retrospective retro) {
        repo.create(retro);
    }
}
