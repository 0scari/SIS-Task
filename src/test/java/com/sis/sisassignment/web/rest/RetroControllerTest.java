package com.sis.sisassignment.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sis.sisassignment.RetroMockDataFactory;
import com.sis.sisassignment.domain.model.Retrospective;
import com.sis.sisassignment.domain.service.RetroService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RetroController.class)
@AutoConfigureMockMvc
public class RetroControllerTest {

    private final String URL = "/api/retro/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RetroService service;

    @Test
    public void testBadRequest400() throws Exception {
        Retrospective retro = new Retrospective();

        doNothing().when(service).save(any());

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(retro)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPost201() throws Exception {
        Retrospective retro = RetroMockDataFactory.getDefaultRetro();

        doNothing().when(service).save(any(Retrospective.class));

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(retro)))
                .andExpect(status().isCreated());
    }
}
