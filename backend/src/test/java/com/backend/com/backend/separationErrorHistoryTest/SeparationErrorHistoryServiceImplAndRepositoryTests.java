package com.backend.com.backend.separationErrorHistoryTest;

import com.backend.com.backend.entities.SeparationErrorHistory;
import com.backend.com.backend.repositories.SeparationErrorHistoryRepository;
import com.backend.com.backend.services.impl.SeparationErrorHistoryServiceImpl;
import com.backend.com.backend.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SeparationErrorHistoryServiceImplAndRepositoryTests {

    @InjectMocks
    private SeparationErrorHistoryServiceImpl errorHistoryService;

    @Mock
    private SeparationErrorHistoryRepository errorHistoryRepository;

    private SeparationErrorHistory separationErrorHistory;

    @BeforeEach
    public void setUp(){
        separationErrorHistory = new SeparationErrorHistory(1L, "Joao", new Date(), 123,
                777, 3, 7, 7);
    }
    @Test
    public void testFindAll(){
        List<SeparationErrorHistory> errorHistoryList = Collections.singletonList(separationErrorHistory);
        when(errorHistoryRepository.findAll()).thenReturn(errorHistoryList);

        List<SeparationErrorHistory> result = errorHistoryService.findAll();
        assertEquals(1, result.size());
        assertEquals(separationErrorHistory, result.get(0));
    }
    @Test
    public void testFindById(){
        when(errorHistoryRepository.findById(1L)).thenReturn(Optional.of(separationErrorHistory));

        SeparationErrorHistory result = errorHistoryService.findById(1L);
        assertEquals(separationErrorHistory, result);
    }
    @Test
    public void testFindById_NotFound(){
        when(errorHistoryRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> errorHistoryService.findById(1L));
    }
    @Test
    public void testCreateErrorHistory(){
        SeparationErrorHistory separationErrorHistory = new SeparationErrorHistory();
        when(errorHistoryRepository.save(separationErrorHistory)).thenReturn(separationErrorHistory);

        SeparationErrorHistory result = errorHistoryService.createErrorHistory(separationErrorHistory);
        assertEquals(separationErrorHistory, result);
    }
    @Test
    public void testErrorHistoryByCodProduct(){
        separationErrorHistory.setCodProduct(123);

        List<SeparationErrorHistory> errorHistoryList = Collections.singletonList(separationErrorHistory);
        when(errorHistoryRepository.findByCodProduct(123)).thenReturn(errorHistoryList);

        List<SeparationErrorHistory> result = errorHistoryService.getErrorHistoryByCodProduct(123);
        assertEquals(1, result.size());
        assertEquals(123, result.get(0).getCodProduct());
    }
}
