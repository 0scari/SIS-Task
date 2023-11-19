package com.sis.sisassignment.domain.service;

import com.sis.sisassignment.RetroMockDataFactory;
import com.sis.sisassignment.domain.model.FeedbackItem;
import com.sis.sisassignment.domain.model.Retrospective;
import com.sis.sisassignment.persistence.repository.RetroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests are intended to be demonstrative rather than exhaustive.
 */
@ExtendWith(MockitoExtension.class)
public class RetroServiceTest {

    @Mock
    private RetroRepository mockRepo;

    @InjectMocks
    private RetroService retroService;

    @Test
    public void testSave() {
        Retrospective retro = RetroMockDataFactory.getDefaultRetro();

        retroService.save(retro);

        verify(mockRepo).create(retro);
    }

    @Test
    public void testLoadRetroExists() {
        Retrospective retro = RetroMockDataFactory.getDefaultRetro();

        when(mockRepo.read(retro.getName())).thenReturn(retro);

        Optional<Retrospective> loadedRetro = retroService.loadRetro(retro.getName());

        // Verify that the read method in the repository is called with the correct retroName
        verify(mockRepo).read(retro.getName());

        // Check that the loaded Retro is present
        assertTrue(loadedRetro.isPresent());
    }

    @Test
    public void testLoadRetroNotExists() {
        when(mockRepo.read("NonExistentRetro")).thenReturn(null);

        Optional<Retrospective> loadedRetro = retroService.loadRetro("NonExistentRetro");

        // Verify that the read method in the repository is called with the correct retroName
        verify(mockRepo).read("NonExistentRetro");

        // Check that the loaded Retro is not present
        assertFalse(loadedRetro.isPresent());
    }

    @Test
    public void testAddFeedback() {
        Retrospective retro = RetroMockDataFactory.getDefaultRetro();
        FeedbackItem feedback = RetroMockDataFactory.getFooFeedback();

        retroService.addFeedback(retro, feedback);

        // Verify that the update method in the repository is called with the correct Retrospective object
        verify(mockRepo).update(retro);

        // Check that the feedback item is added to the Retro
        assertEquals(1, retro.getFeedbackItems().size());
        assertEquals(feedback, retro.getFeedbackItems().get(0));
    }
}
