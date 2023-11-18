package com.sis.sisassignment.web.rest;

import com.sis.sisassignment.domain.model.FeedbackItem;
import com.sis.sisassignment.domain.model.Retrospective;
import com.sis.sisassignment.domain.service.RetroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.Optional;

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
    public ResponseEntity<String> postFeedback(@RequestBody @Valid FeedbackItem feedback, @RequestParam String retroName) {

        Optional<Retrospective> retro = this.retroService.loadRetro(retroName);
        if (!retro.isPresent()) {
            return buildRetroNotFoundResponse(retroName);
        }

        this.retroService.addFeedback(retro.get(), feedback);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/feedback", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putFeedback(@RequestBody @Valid FeedbackItem newFeedback, @RequestParam String retroName) {

        Optional<Retrospective> retro = this.retroService.loadRetro(retroName);
        if (!retro.isPresent()) {
            return buildRetroNotFoundResponse(retroName);
        }

        Optional<FeedbackItem> oldFeedback = this.retroService.loadFeedback(retro.get(), newFeedback.getParticipantName());
        if (!oldFeedback.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(String.format("Feedback for participant [%s] does not exist", newFeedback.getParticipantName()));
        }

        this.retroService.updateFeedback(retro.get(), oldFeedback.get(), newFeedback);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<String> buildRetroNotFoundResponse(String retroName) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(String.format("Retrospective [%s] does not exist", retroName));
    }
}
