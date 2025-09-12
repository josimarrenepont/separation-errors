package com.backend.com.backend.separationTest;

import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.repositories.SeparationRepository;
import com.backend.com.backend.services.impl.SeparationServiceImpl;
import com.backend.com.backend.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeAll;
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
public class SeparationServiceImplAndRepositoryTests {

    @InjectMocks
    private SeparationServiceImpl separationServiceImpl;

    @Mock
    private SeparationRepository separationRepository;

    private static Separation separation;

    @BeforeAll
    public static void setUp(){
        separation = new Separation(1L, new Date(), "Test Separation",
                123, 2, 5, 3, 1);
    }
    @Test
    public void testFindAll(){
        List<Separation> separationList = Collections.singletonList(separation);
        when(separationRepository.findAll()).thenReturn(separationList);

        List<Separation> result = separationServiceImpl.findAll();

        assertEquals(1, result.size());
        assertEquals(separation, result.get(0));
    }
    @Test
    public void testFindById(){
        when(separationRepository.findById(1L)).thenReturn(Optional.of(separation));

        Separation result = separationServiceImpl.findById(1L);

        assertEquals(separation, result);
    }
    @Test
    public void testFindById_NotFound(){
        when(separationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> separationServiceImpl.findById(1L));
    }
    @Test
    public void testSave(){
        when(separationRepository.save(separation)).thenReturn(separation);

        Separation result = separationRepository.save(separation);

        assertEquals(separation, result);
    }
    @Test
    public void testUpdateErrors(){
        Separation updateSeparation = new Separation(1L, new Date(), "Updated Separation",
                20, 5, 3, 2, 10);
        when(separationRepository.findById(1L)).thenReturn(Optional.of(separation));
        when(separationRepository.save(separation)).thenReturn(updateSeparation);

        Separation result = separationServiceImpl.updateErrors(1L, updateSeparation);

        assertEquals(updateSeparation, result);
    }
    @Test
    public void testUpdateErrors_NotFound(){
        when(separationRepository.findById(1L)).thenReturn(Optional.empty());

        Separation errorData = new Separation(1L, new Date(), "Updated Separation",
                20, 5, 3, 2, 10);

        assertThrows(ResourceNotFoundException.class, () -> separationServiceImpl.updateErrors(1L, errorData));
    }
}
