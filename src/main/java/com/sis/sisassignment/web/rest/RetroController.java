package com.sis.sisassignment.web.rest;

import com.sis.sisassignment.domain.model.Retrospective;
import com.sis.sisassignment.persistence.repository.RetroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/retro")
public class RetroController {

    @Autowired
    private RetroRepository repository;

    @GetMapping("/")
    public List<Retrospective> get() {
        return this.repository.readAll();
    }

    @PostMapping("/")
    public String post(@RequestBody @Valid Retrospective retro) {
        this.repository.create(retro);
        Instant.now();
        return "Created";
    }
}
