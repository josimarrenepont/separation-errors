package com.backend.com.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.repositories.SeparationRepository;

@Service
public class SeparationSeervice {

    @Autowired
    private SeparationRepository repository;

    public List<Separation> findAll() {
        return repository.findAll();
    }
}