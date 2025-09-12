package com.backend.com.backend.separationErrorHistoryTest;

import com.backend.com.backend.controllers.SeparationErrorHistoryController;
import com.backend.com.backend.entities.SeparationErrorHistory;
import com.backend.com.backend.services.impl.SeparationErrorHistoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class SeparationErrorHistoryControllerTest {

    @InjectMocks
    private SeparationErrorHistoryController separationErrorHistoryController;

    @Mock
    private SeparationErrorHistoryServiceImpl errorHistoryService;

    private SeparationErrorHistory separationErrorHistory;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(separationErrorHistoryController).build();
        separationErrorHistory = new SeparationErrorHistory(1L, "Joao", new Date(), 123,
                777, 3, 7, 7);
    }
    @Test
    public void testFindAll() throws Exception{
        List<SeparationErrorHistory> errorHistoryList = Collections.singletonList(separationErrorHistory);
        when(errorHistoryService.findAll()).thenReturn(errorHistoryList);

        ResultActions result = mockMvc.perform(get("/separation-error-history")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$[0].id").value(separationErrorHistory.getId()))
                .andExpect((ResultMatcher) jsonPath("$[0].name").value(separationErrorHistory.getName()));
    }
    @Test
    public void testFindById() throws Exception{
        when(errorHistoryService.findById(1L)).thenReturn(separationErrorHistory);

        ResultActions result = mockMvc.perform(get("/separation-error-history/1")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$.id").value(separationErrorHistory.getId()))
                .andExpect((ResultMatcher) jsonPath("$.name").value(separationErrorHistory.getName()));
    }
    @Test
    public void testErrorHistoryByCodProduct() throws Exception{
        List<SeparationErrorHistory> errorHistoryList = Collections.singletonList(separationErrorHistory);
        when(errorHistoryService.getErrorHistoryByCodProduct(123)).thenReturn(errorHistoryList);

        ResultActions result = mockMvc.perform(get("/separation-error-history/error-history/123")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$[0].id").value(separationErrorHistory.getId()))
                .andExpect((ResultMatcher) jsonPath("$[0].codProduct").value(separationErrorHistory.getCodProduct()));
    }
}
