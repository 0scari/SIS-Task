package com.sis.sisassignment.domain.service;

import com.sis.sisassignment.domain.model.FeedbackItem;
import com.sis.sisassignment.domain.model.Retrospective;
import com.sis.sisassignment.persistence.repository.RetroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RetroService {

    private final RetroRepository repo;

    public void save(Retrospective retro) {
        repo.create(retro);
    }

    public Optional<Retrospective> loadRetro(String retroName) {
        return Optional.ofNullable(repo.read(retroName));
    }

    public void addFeedback(Retrospective retro, FeedbackItem feedback) {
        retro.getFeedbackItems().add(feedback);
        repo.update(retro);
    }

    public void updateFeedback(Retrospective retro, FeedbackItem oldFeedback, FeedbackItem newFeedback) {
        List<FeedbackItem> feedbackList = retro.getFeedbackItems();
        feedbackList.remove(oldFeedback);
        feedbackList.add(newFeedback);
    }

    public Optional<FeedbackItem> loadFeedback(Retrospective retro, String participantName) {
        return retro.getFeedbackItems().stream()
                .filter(f -> f.getParticipantName().equals(participantName))
                .findFirst();
    }
}
