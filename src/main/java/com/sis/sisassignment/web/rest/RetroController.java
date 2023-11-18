package com.sis.sisassignment.web.rest;

import com.sis.sisassignment.domain.model.FeedbackItem;
import com.sis.sisassignment.domain.model.Retrospective;
import com.sis.sisassignment.domain.service.RetroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/retro")
@RequiredArgsConstructor
public class RetroController {

    private final RetroService retroService;

    @PostMapping(value = "/", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> post(@RequestBody @Valid Retrospective retro) {
        this.retroService.save(retro);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/feedback", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> postFeedback(@RequestBody @Valid FeedbackItem retro, @RequestParam String retroName) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
