package com.backend.com.backend.services;

import com.backend.com.backend.entities.SeparationErrorHistory;
import com.backend.com.backend.repositories.SeparationErrorHistoryRepository;
import com.backend.com.backend.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeparationErrorHistoryService {

    @Autowired
    private SeparationErrorHistoryRepository errorHistoryRepository;


    public List<SeparationErrorHistory> findAll(){
        return errorHistoryRepository.findAll();
    }

    public SeparationErrorHistory findById(Long id){
        Optional<SeparationErrorHistory> obj = errorHistoryRepository.findById(id);
        return obj.orElseThrow(()-> new ResourceNotFoundException(id));

    }

    public SeparationErrorHistory createErrorHistory(SeparationErrorHistory errorHistory) {
        return errorHistoryRepository.save(errorHistory);
    }
}
