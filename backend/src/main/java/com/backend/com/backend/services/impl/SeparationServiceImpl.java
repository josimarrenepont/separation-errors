package com.backend.com.backend.services.impl;

import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.entities.SeparationErrorHistory;
import com.backend.com.backend.entities.dto.SeparationRequestDTO;
import com.backend.com.backend.repositories.SeparationErrorHistoryRepository;
import com.backend.com.backend.repositories.SeparationRepository;
import com.backend.com.backend.services.SeparationService;
import com.backend.com.backend.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SeparationServiceImpl implements SeparationService {


    private final SeparationRepository separationRepository;
    private final SeparationErrorHistoryRepository separationErrorHistoryRepository;

    public SeparationServiceImpl(SeparationRepository separationRepository, SeparationErrorHistoryRepository separationErrorHistoryRepository) {
        this.separationRepository = separationRepository;
        this.separationErrorHistoryRepository = separationErrorHistoryRepository;
    }

    private Separation savedSeparation;

    @Override
    public List<Separation> findAll() {
        return (List<Separation>) separationRepository.findAll();
    }

    @Override
    public Separation findById(Long id) {
        Optional<Separation> obj = separationRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public Separation save(Separation separation) {
        return separationRepository.save(separation);
    }

    @Override
    @Transactional
    public Separation updateErrors(Long separationId, Separation errorData) {
        Separation separation = separationRepository.findById(separationId)
                .orElseThrow(() -> new ResourceNotFoundException(separationId));

        // Criando uma nova instância de SeparationErrorHistory
        SeparationErrorHistory errorHistory = new SeparationErrorHistory();
        errorHistory.setId(errorData.getId());
        errorHistory.setDate(new Date());
        errorHistory.setName(errorData.getName());
        errorHistory.setCodProduct(errorData.getCodProduct());
        errorHistory.setPallet(errorData.getPallet());
        errorHistory.setErrorPcMais(errorData.getErrorPcMais());
        errorHistory.setErrorPcMenos(errorData.getErrorPcMenos());
        errorHistory.setErrorPcErrada(errorData.getErrorPcErrada());

        // Adicionando o histórico de erro à coleção
        separation.addErrorHistory(errorHistory);

        // Atualizando os campos relevantes no objeto Separation
        separation.setId(errorData.getId());
        separation.setDate(new Date());
        separation.setName(errorData.getName());
        separation.setPallet(errorData.getPallet());
        separation.setCodProduct(errorData.getCodProduct());
        separation.setErrorPcMais(errorData.getErrorPcMais());
        separation.setErrorPcMenos(errorData.getErrorPcMenos());
        separation.setErrorPcErrada(errorData.getErrorPcErrada());

        // Salvando a separação atualizada no repositório
        return separationRepository.save(separation);
    }

    @Override
    public Separation createSeparation(Separation separation) {
        return separationRepository.save(separation);
    }

    @Override
    public Separation getSeparationById(Long id) {
        return separationRepository.getReferenceById(id);
    }

    @Override
    public Separation addError(Separation newError) {
        return separationRepository.save(newError);
    }

    @Override
    public Separation updateErrors(Long id, SeparationRequestDTO errorData) {
        return separationRepository.getReferenceById(id);
    }
}
