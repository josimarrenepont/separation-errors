package com.backend.com.backend.services;

import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.entities.SeparationErrorHistory;
import com.backend.com.backend.entities.dto.SeparationRequestDTO;
import com.backend.com.backend.repositories.SeparationRepository;
import com.backend.com.backend.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SeparationService {

    @Autowired
    private SeparationRepository separationRepository;
    private Separation existingSeparation;

    private Separation separation;

    private SeparationErrorHistory errorHistory;
    private Separation savedSeparation;
    private Long entityId;

    @Autowired
    public SeparationService(SeparationRepository separationRepository) {
        this.separationRepository = separationRepository;
    }

    public void updateFields(Long entityId, Integer pcMais, Integer errorPcMais,
                             Integer pcMenos, Integer errorPcMenos,
                             Integer pcErrada, Integer errorPcErrada) {
        Separation separation = separationRepository.findById(entityId).orElse(null);
        if (separation != null) {
            // Obtenha os valores atuais
            Integer currentPcMais = separation.getPcMais();
            Integer currentErrorPcMais = separation.getErrorPcMais();
            Integer currentPcMenos = separation.getPcMenos();
            Integer currentErrorPcMenos = separation.getErrorPcMenos();
            Integer currentPcErrada = separation.getPcErrada();
            Integer currentErrorPcErrada = separation.getErrorPcErrada();

            // Some os novos valores aos valores atuais
            Integer updatedPcMais = currentPcMais + pcMais;
            Integer updatedErrorPcMais = currentErrorPcMais + errorPcMais;
            Integer updatedPcMenos = currentPcMenos + pcMenos;
            Integer updatedErrorPcMenos = currentErrorPcMenos + errorPcMenos;
            Integer updatedPcErrada = currentPcErrada + pcErrada;
            Integer updatedErrorPcErrada = currentErrorPcErrada + errorPcErrada;

            // Atualize os campos no objeto Separation
            separation.setPcMais(pcMais);
            separation.setErrorPcMais(errorPcMais);
            separation.setPcMenos(pcMenos);
            separation.setErrorPcMenos(errorPcMenos);
            separation.setPcErrada(pcErrada);
            separation.setErrorPcErrada(errorPcErrada);

            // Atualize os campos subTot correspondentes
            separation.setSubTotPcMais(pcMais + errorPcMais);
            separation.setSubTotPcMenos(pcMenos + errorPcMenos);
            separation.setSubTotPcErrada(pcErrada + errorPcErrada);

            updateSeparation(separation);

        }
    }
    public List<Separation> findAll() {
        return (List<Separation>) separationRepository.findAll();
    }


    public Separation findById(Long id) {
        Optional<Separation> obj = separationRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Separation save(SeparationRequestDTO separation) {
        return separationRepository.save(separation);
    }

    public Separation updateErrors(Long separationId, SeparationRequestDTO errorData) {
        Separation separation = separationRepository.findById(separationId)
                .orElseThrow(() -> new ResourceNotFoundException(separationId));

        // Crie uma nova instância de SeparationErrorHistory
        SeparationErrorHistory errorHistory = new SeparationErrorHistory();
        errorHistory.setDate(new Date());
        errorHistory.setName(errorData.getName());
        errorHistory.setCodProduct(errorData.getCodProduct());
        errorHistory.setPallet(errorData.getPallet());
        errorHistory.setErrorPcMais(errorData.getErrorPcMais());
        errorHistory.setErrorPcMenos(errorData.getErrorPcMenos());
        errorHistory.setErrorPcErrada(errorData.getErrorPcErrada());
        errorHistory.setPcMais(errorData.getPcMais());
        errorHistory.setPcMenos(errorData.getPcMenos());
        errorHistory.setPcErrada(errorData.getPcErrada());

        // Adicione o histórico de erro à coleção
        separation.addErrorHistory(errorHistory);

        // Atualize os campos relevantes no objeto Separation
        separation.setPcMais(errorData.getPcMais());
        separation.setErrorPcMais(errorData.getErrorPcMais());

        // Salve a separação atualizada no repositório
        return separationRepository.save(separation);
    }


    public Separation createSeparation(Separation separation) {
        return separationRepository.save(separation);
    }

    public Separation getSeparationById(Long id) {
        return separationRepository.getReferenceById(id);
        
    }

    public Separation addError(SeparationRequestDTO newError) {
        return separationRepository.save(newError);

    }

    public Integer getAccumulatedSumTotPcMenos() {
        separationRepository.save(separation).getSubTotPcMais();
        return getAccumulatedSumTotPcMenos();
    }


    public Integer getAccumulatedSumTotPcMais() {
        separationRepository.save(separation).getSubTotPcErrada();
        return getAccumulatedSumTotPcMais();
    }

    public List<Separation> getAllSeparations() {
        separationRepository.save(separation);
        return getAllSeparations();
    }

    public Separation saveSeparation(Separation separation) {
        separationRepository.save(separation);
        return saveSeparation(separation);
    }

    public void updateAccumulatedSumOfErrors(Separation savedSeparation) {
        this.savedSeparation = savedSeparation;
        savedSeparation.updateAccumulatedSumOfErrors();
    }


    public Separation updateSeparation(Separation updatedSeparation) {

        System.out.println("Antes da atualização: " + separationRepository.findById(updatedSeparation.getId()));
        // Verifica se os campos de erro não são nulos e diferentes de zero
        if (updatedSeparation.getErrorPcMais() != null && updatedSeparation.getErrorPcMais() != 0) {
            updatedSeparation.setSubTotPcMais(updatedSeparation.getSubTotPcMais() + updatedSeparation.getErrorPcMais());
        }

        if (updatedSeparation.getErrorPcMenos() != null && updatedSeparation.getErrorPcMenos() != 0) {
            updatedSeparation.setSubTotPcMenos(updatedSeparation.getSubTotPcMenos() + updatedSeparation.getErrorPcMenos());
        }

        if (updatedSeparation.getErrorPcErrada() != null && updatedSeparation.getErrorPcErrada() != 0) {
            updatedSeparation.setSubTotPcErrada(updatedSeparation.getSubTotPcErrada() + updatedSeparation.getErrorPcErrada());
        }
        if(updatedSeparation.getPcMais() != null && updatedSeparation.getPcMais() != 0){
            updatedSeparation.setPcMais(updatedSeparation.getPcMais() + updatedSeparation.getPcMais());
        }
        if(updatedSeparation.getPcMenos() != null && updatedSeparation.getPcMenos() != 0){
            updatedSeparation.setPcMenos(updatedSeparation.getPcMenos() + updatedSeparation.getPcMenos());
        }
        if(updatedSeparation.getPcErrada() != null && updatedSeparation.getPcErrada() != 0){
            updatedSeparation.setPcErrada(updatedSeparation.getPcErrada() + updatedSeparation.getPcErrada());
        }

        System.out.println("Após a atualização: " + separationRepository.findById(updatedSeparation.getId()));
        return separationRepository.save(updatedSeparation);

    }
}





