package com.backend.com.backend.separationTest;

import com.backend.com.backend.controllers.SeparationController;
import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.services.impl.SeparationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class SeparationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SeparationServiceImpl separationServiceImpl;

    @InjectMocks
    private SeparationController separationController;

    private Separation separation;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(separationController).build();

        separation = new Separation(1L, new Date(), "Test Separation",
                100, 5, 7, 1, 3);
    }
    @Test
    public void testFindAll() throws Exception{
        List<Separation> separationList = Collections.singletonList(separation);
        when(separationServiceImpl.findAll()).thenReturn(separationList);

        ResultActions result = mockMvc.perform(get("/separations")
                        .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$[0].id").value(separation.getId()))
                .andExpect((ResultMatcher) jsonPath("$[0].name").value(separation.getName()));
    }
    @Test
    public void testFindById() throws Exception{
        when(separationServiceImpl.findById(1L)).thenReturn(separation);

        ResultActions result = mockMvc.perform(get("/separations/1")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$.id").value(separation.getId()))
                .andExpect((ResultMatcher) jsonPath("$.name").value(separation.getName()));
    }
    @Test
    public void testUpdateSeparationErrors() throws Exception{
        when(separationServiceImpl.updateErrors(1L, separation)).thenReturn(separation);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(separation);

        ResultActions result = mockMvc.perform(put("/separations/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(separation.getId()))
                    .andExpect(jsonPath("$.name").value(separation.getName()));
    }
    @Test
    public void testAddError() throws Exception{
        when(separationServiceImpl.addError(separation)).thenReturn(separation);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(separation);

        ResultActions result = mockMvc.perform(put("/separations/separationRequestDTO")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody));

        result.andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$.id").value(separation.getId()))
                        .andExpect((ResultMatcher) jsonPath("$.name").value(separation.getName()));
    }
}
