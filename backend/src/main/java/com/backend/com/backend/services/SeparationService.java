package com.backend.com.backend.services;

import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.entities.dto.SeparationRequestDTO;

import java.util.List;

public interface SeparationService {

    List<Separation> findAll();

    Separation findById(Long id);

    Separation save(Separation separation);

    Separation updateErrors(Long separationId, Separation errorData);

    Separation createSeparation(Separation separation);

    Separation getSeparationById(Long id);

    Separation addError(Separation newError);

    Separation updateErrors(Long id, SeparationRequestDTO errorData);
}
