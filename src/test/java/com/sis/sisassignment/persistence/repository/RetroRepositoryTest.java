package com.sis.sisassignment.persistence.repository;

import com.sis.sisassignment.RetroMockDataFactory;
import com.sis.sisassignment.domain.model.Retrospective;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RetroRepositoryTest {

    private RetroRepository retroRepository = new RetroRepository();

    @Test
    public void testCreateAndRead() {
        Retrospective retro = RetroMockDataFactory.getDefaultRetro();

        // Create a Retro
        retroRepository.create(retro);

        // Read the Retro
        assertNotNull(retroRepository.read(retro.getName()));
    }

    @Test
    public void testReadNonExistentRetro() {
        // Attempt to read a non-existent Retro
        Retrospective retrievedRetro = retroRepository.read("NonExistentRetro");

        // Check that the retrieved Retro is null
        assertNull(retrievedRetro);
    }

}
