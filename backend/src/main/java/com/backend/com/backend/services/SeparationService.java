package com.backend.com.backend.services;

import java.util.List;
import java.util.Optional;

import com.backend.com.backend.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.entities.dto.SeparationRequestDTO;
import com.backend.com.backend.repositories.SeparationRepository;
import com.backend.com.backend.services.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class SeparationService {

    @Autowired
    private SeparationRepository separationRepository;
    private Separation existingSeparation;

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

    public Separation updateErrors(Long employeeId, Employee errorData) {

        Optional<Separation> optionalSeparation = separationRepository.findById(employeeId);

        if (optionalSeparation.isEmpty()) {

            throw new ResourceNotFoundException(employeeId);

        }

        Separation separation = optionalSeparation.get();

        Integer pcMais = errorData.getTotPcMais();

        Integer pcMenos = errorData.getTotPcMenos();

        Integer pcErrada = errorData.getTotPcErrada();

        // Atualize os erros do funcionário

        separation.setPcMais(pcMais);

        separation.setPcMenos(pcMenos);

        separation.setPcErrada(pcErrada);

        // Salve a separação atualizada

        return separationRepository.save(separation);

    }
    @PostMapping("/separations")
    public List<Separation> getAllSeparations() {
        return getAllSeparations();
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

