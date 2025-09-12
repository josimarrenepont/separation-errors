package com.backend.com.backend.services;

import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.entities.SeparationErrorHistory;

import java.util.List;

public interface SeparationErrorHistoryService {

    List<SeparationErrorHistory> findAll();

    SeparationErrorHistory findById(Long id);

    SeparationErrorHistory createErrorHistory(SeparationErrorHistory errorHistory);

    void updateAccumulatedSumOfErrors(Separation savedSeparation);

    List<SeparationErrorHistory> getErrorHistoryByCodProduct(Integer codProduct);
}
