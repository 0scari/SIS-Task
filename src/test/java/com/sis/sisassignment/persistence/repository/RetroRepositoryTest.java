package com.sis.sisassignment.persistence.repository;

import com.sis.sisassignment.RetroMockDataFactory;
import com.sis.sisassignment.domain.model.Retrospective;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests are intended to be demonstrative rather than exhaustive.
 */
public class RetroRepositoryTest {

    private RetroRepository retroRepository;
    private Retrospective sampleRetrospective1;
    private Retrospective sampleRetrospective2;

    @BeforeEach
    public void setUp() {
        retroRepository = new RetroRepository();
        sampleRetrospective1 = Retrospective.builder()
                .name("SampleRetro1")
                .summary("Summary1")
                .date(Instant.now())
                .build();
        sampleRetrospective2 = Retrospective.builder()
                .name("SampleRetro2")
                .summary("Summary2")
                .date(Instant.now().minus(1, ChronoUnit.DAYS))
                .build();
        retroRepository.create(sampleRetrospective1);
        retroRepository.create(sampleRetrospective2);
    }

    @Test
    public void testCreateAndRead() {
        // Create a Retro
        Retrospective newRetrospective = RetroMockDataFactory.getDefaultRetro();
        retroRepository.create(newRetrospective);

        // Read the Retro
        Retrospective retrievedRetro = retroRepository.read(newRetrospective.getName());

        // Check that the retrieved Retro has the expected values
        assertNotNull(retrievedRetro);
        assertEquals(newRetrospective.getName(), retrievedRetro.getName());
        assertEquals(newRetrospective.getSummary(), retrievedRetro.getSummary());
    }

    @Test
    public void testReadNonExistentRetro() {
        // Attempt to read a non-existent Retro
        Retrospective retrievedRetro = retroRepository.read("NonExistentRetro");

        // Check that the retrieved Retro is null
        assertNull(retrievedRetro);
    }

    @Test
    public void testReadAll() {
        // Read all retrospectives
        List<Retrospective> allRetrospectives = retroRepository.readAll(0, 10);

        // Check that all retrospectives are retrieved
        assertEquals(2, allRetrospectives.size());
        assertTrue(allRetrospectives.containsAll(Arrays.asList(sampleRetrospective1, sampleRetrospective2)));
    }

    @Test
    public void testReadAllWithDate() {
        // Read retrospectives for a specific date
        List<Retrospective> retrospectivesForDate = retroRepository.readAll(0, 10, Instant.now());

        // Check that retrospectives for the specific date are retrieved
        assertEquals(1, retrospectivesForDate.size());
        assertTrue(retrospectivesForDate.contains(sampleRetrospective1));
    }
}
