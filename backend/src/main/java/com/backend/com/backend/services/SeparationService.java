package com.backend.com.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.repositories.SeparationRepository;
import com.backend.com.backend.services.exceptions.ResourceNotFoundException;

@Service
public class SeparationService {

    @Autowired
    private SeparationRepository repository;

    public List<Separation> findAll() {
        return repository.findAll();
    }

    public Separation findById(Long id) {
        Optional<Separation> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Separation save(Separation separation) {
        return repository.save(separation);
    }

	public Separation saveAll(Separation separation, Long id) {
		
		return saveAll(separation, id);
	}

}