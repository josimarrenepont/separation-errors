package com.backend.com.backend.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.repositories.SeparationRepository;
import com.backend.com.backend.services.exceptions.ResourceNotFoundException;

@Service
public class SeparationService {

    @Autowired
    private SeparationRepository separationRepository;

    public List<Separation> findAll() {
        return separationRepository.findAll();
    }

    public Separation findById(Long id) {
        Optional<Separation> obj = separationRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Separation save(Separation separation) {
        return separationRepository.save(separation);
    }

    public Separation updateErrors(Long employeeId, Map<String, Integer> errorData) {

        Optional<Separation> optionalSeparation = separationRepository.findById(employeeId);

        if (optionalSeparation.isEmpty()) {

            throw new ResourceNotFoundException(employeeId);

        }

        Separation separation = optionalSeparation.get();

        Integer pcMais = errorData.get("pcMais");

        Integer pcMenos = errorData.get("pcMenos");

        Integer pcErrada = errorData.get("pcErrada");

        // Atualize os erros do funcionário

        separation.setPcMais(pcMais);

        separation.setPcMenos(pcMenos);

        separation.setPcErrada(pcErrada);

        // Salve a separação atualizada

        return separationRepository.save(separation);

    }

    public List<Separation> getAllSeparations() {
        return getAllSeparations();
    }
}
