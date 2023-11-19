package com.sis.sisassignment.domain.service;

import com.sis.sisassignment.domain.model.FeedbackItem;
import com.sis.sisassignment.domain.model.Retrospective;
import com.sis.sisassignment.persistence.repository.RetroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
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

    public List<Retrospective> loadRetros(int currentPage, int pageSize) {
        return this.repo.readAll(calcPaginationOffset(currentPage, pageSize), pageSize);
    }

    public List<Retrospective> loadRetros(int currentPage, int pageSize, Instant date) {
        return this.repo.readAll(calcPaginationOffset(currentPage, pageSize), pageSize, date);
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

    private int calcPaginationOffset(int currentPage, int pageSize) {
        return (currentPage - 1) * pageSize;
    }
}
