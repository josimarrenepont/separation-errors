package com.backend.com.backend.repositories;

import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.entities.SeparationErrorHistory;
import com.backend.com.backend.entities.dto.SeparationRequestDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeparationErrorHistoryRepository extends JpaRepository<SeparationErrorHistory, Long> {


    static Separation save(Separation separation) {
        return separation;
    }

    static Optional<Object> save(Long separationId, SeparationRequestDTO errorData) {
        return save(separationId, errorData);
    }
}
