package com.backend.com.backend.services;

import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.entities.SeparationErrorHistory;
import com.backend.com.backend.entities.dto.SeparationRequestDTO;
import com.backend.com.backend.repositories.SeparationErrorHistoryRepository;
import com.backend.com.backend.repositories.SeparationRepository;
import com.backend.com.backend.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SeparationService {
    @Autowired
    private SeparationRepository separationRepository;

    @Autowired
    private SeparationErrorHistoryRepository separationErrorHistoryRepository;
    private Separation separation;

    private Separation savedSeparation;

    @Autowired
    public SeparationService(SeparationRepository separationRepository) {
        this.separationRepository = separationRepository;
    }

    public List<Separation> findAll() {
        return (List<Separation>) separationRepository.findAll();
    }


    public Separation findById(Long id) {
        Optional<Separation> obj = separationRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Separation save(Separation separation) {
        return separationRepository.save(separation);
    }

    @Transactional
    public Separation updateErrors(Long separationId, Separation errorData) {
        Separation separation = separationRepository.findById(separationId)
                .orElseThrow(() -> new ResourceNotFoundException(separationId));

        // Crie uma nova instância de SeparationErrorHistory
        SeparationErrorHistory errorHistory = new SeparationErrorHistory();
        errorHistory.setId(errorData.getId());
        errorHistory.setDate(new Date());
        errorHistory.setName(errorData.getName());
        errorHistory.setCodProduct(errorData.getCodProduct());
        errorHistory.setPallet(errorData.getPallet());
        errorHistory.setErrorPcMais(errorData.getErrorPcMais());
        errorHistory.setErrorPcMenos(errorData.getErrorPcMenos());
        errorHistory.setErrorPcErrada(errorData.getErrorPcErrada());

        // Adicione o histórico de erro à coleção
        separation.addErrorHistory(errorHistory);

        // Atualize os campos relevantes no objeto Separation

        separation.setId(errorData.getId());
        separation.setDate(new Date());
        separation.setName(errorData.getName());
        separation.setPallet(errorData.getPallet());
        separation.setCodProduct(errorData.getCodProduct());
        separation.setErrorPcMais(errorData.getErrorPcMais());
        separation.setErrorPcMenos(errorData.getErrorPcMenos());
        separation.setErrorPcErrada(errorData.getErrorPcErrada());;

        // Salve a separação atualizada no repositório
        return separationRepository.save(separation);
    }

    public Separation createSeparation(Separation separation) {
        return separationRepository.save(separation);
    }

    public Separation getSeparationById(Long id) {
        return separationRepository.getReferenceById(id);

    }

    public Separation addError(Separation newError) {
        return separationRepository.save(newError);

    }

    public Separation updateErrors(Long id, SeparationRequestDTO errorData) {
        return separationRepository.getReferenceById(id);
    }
}





