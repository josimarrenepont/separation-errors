package com.backend.com.backend.services;

import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.entities.SeparationErrorHistory;
import com.backend.com.backend.entities.dto.SeparationRequestDTO;
import com.backend.com.backend.repositories.SeparationErrorHistoryRepository;
import com.backend.com.backend.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SeparationErrorHistoryService {

    @Autowired
    private SeparationErrorHistoryRepository errorHistoryRepository;

    @Autowired
    private SeparationErrorHistoryRepository separationErrorHistoryRepository;
    private SeparationErrorHistory errorHistory;


    public List<SeparationErrorHistory> findAll(){
        return errorHistoryRepository.findAll();
    }

    public SeparationErrorHistory findById(Long id){
        Optional<SeparationErrorHistory> obj = errorHistoryRepository.findById(id);
        return obj.orElseThrow(()-> new ResourceNotFoundException(id));

    }

    public SeparationErrorHistory createErrorHistory(SeparationErrorHistory errorHistory) {
        return errorHistoryRepository.save(errorHistory);
    }

    public Separation updateSeparationErrors(Long separationId, SeparationRequestDTO errorData) {

        Separation separation = (Separation) SeparationErrorHistoryRepository.save(separationId, errorData).orElseThrow(() -> new ResourceNotFoundException(separationId));

// Seus cálculos para atualizar os erros vão aqui
        SeparationRequestDTO requestDTO = new SeparationRequestDTO();
        requestDTO.setDate(new Date());
        requestDTO.setName(errorData.getName());
        requestDTO.setCodProduct(errorData.getCodProduct());
        requestDTO.setPallet(errorData.getPallet());
        requestDTO.setErrorPcMais(errorData.getErrorPcMais());
        requestDTO.setErrorPcMenos(errorData.getErrorPcMenos());
        requestDTO.setErrorPcErrada(errorData.getErrorPcErrada());
        requestDTO.setPcMais(errorData.getPcMais());
        requestDTO.setPcMenos(errorData.getPcMenos());
        requestDTO.setPcErrada(errorData.getPcErrada());
        requestDTO.setSumPcMais(errorData.getSumPcMais());
        requestDTO.setSumPcMenos(errorData.getSumPcMenos());
        requestDTO.setSumPcErrada(errorData.getSumPcErrada());

        requestDTO.setSeparation(separation);

        separation.addErrorToHistory(errorHistory);
// Atualize os campos pcMais, pcMenos e pcErrada no objeto Separation

        separation.addErrorTotPcMais(errorData.getErrorPcMais());

        separation.addErrorTotPcMenos(errorData.getErrorPcMenos());

        separation.addErrorTotPcErrada(errorData.getErrorPcErrada());

// Salve a separação atualizada no banco de dados

        return SeparationErrorHistoryRepository.save(separation);

    }


}
