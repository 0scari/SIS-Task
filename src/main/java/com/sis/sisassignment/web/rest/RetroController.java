package com.sis.sisassignment.web.rest;

import com.sis.sisassignment.domain.model.Retrospective;
import com.sis.sisassignment.domain.service.RetroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/retro")
@RequiredArgsConstructor
public class RetroController {

    private final RetroService retroService;

    @PostMapping("/")
    public ResponseEntity<Void> post(@RequestBody @Valid Retrospective retro) {
        this.retroService.save(retro);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
