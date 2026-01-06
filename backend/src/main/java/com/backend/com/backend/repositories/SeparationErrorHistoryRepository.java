package com.backend.com.backend.repositories;

import com.backend.com.backend.entities.SeparationErrorHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SeparationErrorHistoryRepository extends JpaRepository<SeparationErrorHistory, Long> {

    @Transactional
    List<SeparationErrorHistory> findByCodProduct(Integer codProduct);

    List<SeparationErrorHistory> findBySeparationId(Long separationId);
    SeparationErrorHistory getReferenceById(Long id);

   /* List<SeparationErrorHistory> findByDateBetween(Date startDate, Date endDate);*/
}
