package com.backend.com.backend.services;

import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.entities.SeparationErrorHistory;
import com.backend.com.backend.entities.dto.SeparationRequestDTO;
import com.backend.com.backend.repositories.SeparationRepository;
import com.backend.com.backend.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class SeparationService {

    @Autowired
    private SeparationRepository separationRepository;
    private Separation existingSeparation;

    private Separation separation;

    private SeparationErrorHistory errorHistory;

    @Autowired
    public SeparationService(SeparationRepository separationRepository) {
        this.separationRepository = separationRepository;
    }
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

    public Separation updateErrors(Long separationId, SeparationRequestDTO errorData) {
        Separation separation = separationRepository.findById(separationId)
                .orElseThrow(() -> new ResourceNotFoundException(separationId));


        SeparationErrorHistory errorHistoryEntry = new SeparationErrorHistory();
        errorHistoryEntry.setDate(errorData.getDate());
        errorHistoryEntry.setName(errorData.getName());
        errorHistoryEntry.setCodProduct(errorData.getCodProduct());
        errorHistoryEntry.setPallet(errorData.getPallet());
        errorHistoryEntry.setErrorPcMais(errorData.getErrorPcMais());
        errorHistoryEntry.setErrorPcMenos(errorData.getErrorPcMenos());
        errorHistoryEntry.setErrorPcErrada(errorData.getErrorPcErrada());
        errorHistoryEntry.setPcMais(errorData.getPcMais());
        errorHistoryEntry.setPcMenos(errorData.getPcMenos());
        errorHistoryEntry.setPcErrada(errorData.getPcErrada());

        // Defina a relação entre a entrada de histórico de erro e a separação
        errorHistoryEntry.setSeparation(separation);

        // Adicione o histórico de erro à coleção

        // Verifique se a coleção errorHistory está inicializada, crie uma nova lista se não estiver
        if (separation.getErrorHistory() == null) {
            separation.setErrorHistory(new HashSet<>());
        }

// Adicione o histórico de erro à coleção
        separation.getErrorHistory().add(errorHistoryEntry);


        return separationRepository.save(separation);
    }

    public Separation addError(SeparationRequestDTO errorData) {
        // Aqui você pode criar uma nova instância de Separation com os dados fornecidos em errorData
       
    	Separation newError = new Separation();
        newError.setDate(errorData.getDate());
        newError.setName(errorData.getName());
        newError.setPallet(errorData.getPallet());
        newError.setCodProduct(errorData.getCodProduct());
        newError.setPcMais(errorData.getPcMais());
        newError.setPcMenos(errorData.getPcMenos());
        newError.setPcErrada(errorData.getPcErrada());
        newError.setErrorPcMais(errorData.getErrorPcMais());
        newError.setErrorPcMenos(errorData.getErrorPcMenos());
        newError.setErrorPcErrada(errorData.getErrorPcErrada());
        
        // Defina outros campos conforme necessário

        // Agora, você pode salvar a nova instância no banco de dados
        
        return separationRepository.save(newError);
    }

   public Separation separationRequestDTO(Separation requestDTO){
        return separationRepository.save(requestDTO);
   }

    public Separation createSeparation(Separation separation) {
        return separationRepository.save(separation);
    }

    public Separation getSeparationById(Long id) {
        return separationRepository.getReferenceById(id);
        
    }

    public Separation updateSeparation(Separation existingSeparation) {
        return separationRepository.getReferenceById(existingSeparation.getId());
    }
}

