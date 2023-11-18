package com.sis.sisassignment.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sis.sisassignment.RetroMockDataFactory;
import com.sis.sisassignment.domain.model.FeedbackItem;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RetroController.class)
@AutoConfigureMockMvc
public class RetroControllerEtETest {

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

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(retro)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPost201() throws Exception {
        Retrospective retro = RetroMockDataFactory.getDefaultRetro();

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(retro)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testPostFeedback() throws Exception {
        FeedbackItem feedbackItem = RetroMockDataFactory.getBarFeedback();
        String retroName = "Retrospective 123";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/retro/feedback")
                        .param("retroName", retroName)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(feedbackItem)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Retrospective [Retrospective 123] does not exist"));
    }

    @Test
    public void testPostFeedback400WithoutRetroName() throws Exception {
        FeedbackItem feedbackItem = RetroMockDataFactory.getBarFeedback();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/retro/feedback")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(feedbackItem)))
                .andExpect(status().isBadRequest());
    }
}
