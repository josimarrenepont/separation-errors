package com.backend.com.backend.services;

import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.entities.SeparationErrorHistory;
import com.backend.com.backend.entities.dto.SeparationRequestDTO;
import com.backend.com.backend.repositories.SeparationErrorHistoryRepository;
import com.backend.com.backend.repositories.SeparationRepository;
import com.backend.com.backend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
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
    public void updateAccumulatedSumsOfErrors() {
        separationRepository.updateAccumulatedSumsOfErrors();
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
        errorHistory.setPcMais(errorData.getPcMais());
        errorHistory.setPcMenos(errorData.getPcMenos());
        errorHistory.setPcErrada(errorData.getPcErrada());
        errorHistory.setSubTotPcMais(errorData.getSubTotPcMais());
        errorHistory.setSubTotPcMenos(errorData.getSubTotPcMenos());
        errorHistory.setSubTotPcErrada(errorData.getSubTotPcErrada());

        // Adicione o histórico de erro à coleção
        separation.addErrorHistory(errorHistory);

        // Atualize os campos relevantes no objeto Separation

        separation.setId(errorData.getId());
        separation.setDate(new Date());
        separation.setName(errorData.getName());
        separation.setPallet(errorData.getPallet());
        separation.setCodProduct(errorData.getCodProduct());
        separation.setPcMais(errorData.getPcMais());
        separation.setErrorPcMais(errorData.getErrorPcMais());
        separation.setPcMenos(errorData.getPcMenos());
        separation.setErrorPcMenos(errorData.getErrorPcMenos());
        separation.setPcErrada(errorData.getPcErrada());
        separation.setErrorPcErrada(errorData.getErrorPcErrada());
        separation.setSubTotPcMais(errorData.getSubTotPcMais());
        separation.setSubTotPcMenos(errorData.getSubTotPcMenos());
        separation.setSubTotPcErrada(errorData.getSubTotPcErrada());

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

    public Integer getAccumulatedSumTotPcMenos() {
        return separationRepository.sumSubTotPcMenosByDateRange(new Date(), new Date());
    }

    public Integer getAccumulatedSumTotPcMais() {
        return separationRepository.sumSubTotPcMaisByDateRange(new Date(), new Date());
    }

    public Integer getAccumulatedSumTotPcErrada() {
        return separationRepository.sumSubTotPcErradaByDateRange(new Date(), new Date());
    }

    @Transactional
    public void salvarSeparation(Separation separation) {
        // Verifica se os valores de erro não são zero antes de salvar no banco de dados
        if (separation.getErrorPcMais() != 0 || separation.getErrorPcMenos() != 0 || separation.getErrorPcErrada() != 0) {
            separationRepository.save(separation);
        } else {
            // Faça alguma coisa, como registrar um aviso ou ignorar a persistência, caso todos os valores de erro sejam zero
            System.out.println("Todos os valores de erro são zero. Não salvando no banco de dados.");
        }
    }

    @Transactional
    public Separation updateSeparation(Separation updatedSeparation) {
        Optional<Separation> optionalSeparation = separationRepository.findById(updatedSeparation.getId());

        if (optionalSeparation.isPresent()) {
            Separation existingSeparation = optionalSeparation.get();

            // Subtrai os valores antigos
            existingSeparation.setSubTotPcMais(subtract(existingSeparation.getSubTotPcMais(), existingSeparation.getErrorPcMais()) + existingSeparation.getPcMais());
            existingSeparation.setSubTotPcMenos(subtract(existingSeparation.getSubTotPcMenos(), existingSeparation.getErrorPcMenos()) + existingSeparation.getPcMenos());
            existingSeparation.setSubTotPcErrada(subtract(existingSeparation.getSubTotPcErrada(), existingSeparation.getErrorPcErrada())+ existingSeparation.getPcErrada());;

            // Atualiza os campos
            existingSeparation.setDate(updatedSeparation.getDate());
            existingSeparation.setPallet(updatedSeparation.getPallet());
            existingSeparation.setCodProduct(updatedSeparation.getCodProduct());
            existingSeparation.setPcMais(updatedSeparation.getPcMais());
            existingSeparation.setErrorPcMais(updatedSeparation.getErrorPcMais());
            existingSeparation.setPcMenos(updatedSeparation.getPcMenos());
            existingSeparation.setErrorPcMenos(updatedSeparation.getErrorPcMenos());
            existingSeparation.setPcErrada(updatedSeparation.getPcErrada());
            existingSeparation.setErrorPcErrada(updatedSeparation.getErrorPcErrada());
            existingSeparation.setSubTotPcMais(updatedSeparation.getSubTotPcMais());
            existingSeparation.setSubTotPcMenos(updatedSeparation.getSubTotPcMenos());
            existingSeparation.setSubTotPcErrada(updatedSeparation.getSubTotPcErrada());

            // Adiciona os novos valores aos campos, tratando nulos
            existingSeparation.setSubTotPcMais(add(existingSeparation.getSubTotPcMais(), existingSeparation.getErrorPcMais()));
            existingSeparation.setSubTotPcMenos(add(existingSeparation.getSubTotPcMenos(), existingSeparation.getErrorPcMenos()));
            existingSeparation.setSubTotPcErrada(add(existingSeparation.getSubTotPcErrada(), existingSeparation.getErrorPcErrada()));

            // Salva a separação atualizada
            return separationRepository.save(updatedSeparation);
        }

        // Lança uma exceção ou retorna null, dependendo da sua lógica
        throw new EntityNotFoundException("Separation not found with ID: " + updatedSeparation.getId());
    }

    // Função auxiliar para somar, tratando valores nulos
    private Integer add(Integer a, Integer b) {
        return (a != null ? a : 0) + (b != null ? b : 0);
    }

    // Função auxiliar para subtrair, tratando valores nulos
    private Integer subtract(Integer a, Integer b) {
        return (a != null ? a : 0) - (b != null ? b : 0);
    }


    public Separation updateErrors(Long id, SeparationRequestDTO errorData) {
        return separationRepository.getReferenceById(id);
    }
}





