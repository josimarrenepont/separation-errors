package com.backend.com.backend.repositories;

import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.entities.SeparationErrorHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SeparationErrorHistoryRepository extends JpaRepository<SeparationErrorHistory, Long> {

    static Separation save(Separation separation) {
        return separation;
    }

    static Optional<Object> save(Long separationId, Separation errorData) {
        return save(separationId, errorData);
    }
    @Transactional
    List<SeparationErrorHistory> findByCodProduct(Integer codProduct);

    List<SeparationErrorHistory> findBySeparationId(Long separationId);
    SeparationErrorHistory getReferenceById(Long id);

}
