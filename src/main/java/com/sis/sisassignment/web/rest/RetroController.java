package com.sis.sisassignment.web.rest;

import com.sis.sisassignment.domain.model.FeedbackItem;
import com.sis.sisassignment.domain.model.Retrospective;
import com.sis.sisassignment.domain.service.RetroService;
import com.sis.sisassignment.web.rest.exception.RequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RestController
@RequestMapping("/api/retro")
@RequiredArgsConstructor
@Validated
public class RetroController {

    private final RetroService retroService;

    @GetMapping(value = "/", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity<List<Retrospective>> get(@RequestParam @Min(1) Integer currentPage, @RequestParam @Min(1) Integer pageSize) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.retroService.loadRetros(currentPage, pageSize));
    }

    @GetMapping(value = "/by-date", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity<List<Retrospective>> getByDate(@RequestParam @Min(1) Integer currentPage, @RequestParam @Min(1) Integer pageSize, @RequestParam String date) throws RequestException {
        Instant dateInstant;
        try {
            // requirements did not specify about the timezone of the input date
            // so making this assumption, but in real world this would need to be clarified
            dateInstant = Instant.parse(date + "T00:00:00Z");
        } catch (DateTimeParseException ex) {
            throw new RequestException(400, "Invalid Date Was Provided");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.retroService.loadRetros(currentPage, pageSize, dateInstant));
    }

    @PostMapping(value = "/", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> post(@RequestBody @Valid Retrospective retro) {
        if (this.retroService.loadRetro(retro.getName()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(String.format("Retrospective [%s] already exists", retro.getName()));
        }

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
