package com.backend.com.backend.services;

import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.entities.SeparationErrorHistory;
import com.backend.com.backend.entities.dto.SeparationRequestDTO;
import com.backend.com.backend.repositories.SeparationErrorHistoryRepository;
import com.backend.com.backend.repositories.SeparationRepository;
import com.backend.com.backend.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Separation updateErrors(Long separationId, Separation errorData) {
        Separation separation = separationRepository.findById(separationId)
                .orElseThrow(() -> new ResourceNotFoundException(separationId));

        // Crie uma nova instância de SeparationErrorHistory
        SeparationErrorHistory errorHistory = new SeparationErrorHistory();
        errorHistory.setId(separationId);
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
        separation.setId(separationId);
        separation.setDate(new Date());
        separation.setName(errorData.getName());
        separation.setPallet(errorData.getPallet());
        separation.setCodProduct(errorData.getCodProduct());
        separation.setPcMais(errorData.getPcMais());
        separation.setErrorPcMais(errorData.getErrorPcMais());
        separation.setPcMenos(errorData.getPcMenos());
        separation.setPcErrada(errorData.getPcErrada());
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

    public void salvarSeparation(Separation separation) {
        // Verifica se os valores de erro não são zero antes de salvar no banco de dados
        if (separation.getErrorPcMais() != 0 || separation.getErrorPcMenos() != 0 || separation.getErrorPcErrada() != 0) {
            separationRepository.save(separation);
        } else {
            // Faça alguma coisa, como registrar um aviso ou ignorar a persistência, caso todos os valores de erro sejam zero
            System.out.println("Todos os valores de erro são zero. Não salvando no banco de dados.");
        }
    }
    public void salvarSeparations(Separation separation) {
        // Verifica se os valores de erro não são zero antes de salvar no banco de dados
        if (separation.getPcMais() != 0 ||
                separation.getPcMenos() != 0 ||
                separation.getPcErrada() != 0 ||
                separation.getErrorPcMais() !=0 ||
                separation.getErrorPcMenos() != 0 ||
                separation.getErrorPcErrada() !=0) {

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
            return separationRepository.save(existingSeparation);
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


  /*
    public Separation updateSeparationError(Separation updatedSeparation) {
        Long separationId = updatedSeparation.getId();

        // Verificar se a separação existe no banco de dados
        Separation existingSeparation = separationRepository.findById(separationId)
                .orElseThrow(() -> new EntityNotFoundException("Separation not found with id: " + separationId));

        // Atualizar os campos da separação com base nos dados fornecidos
        existingSeparation.setDate(updatedSeparation.getDate());
        existingSeparation.setName(updatedSeparation.getName());
        existingSeparation.setPallet(updatedSeparation.getPallet());
        existingSeparation.setCodProduct(updatedSeparation.getCodProduct());
        existingSeparation.setPcMais(updatedSeparation.getPcMais());
        existingSeparation.setPcMenos(updatedSeparation.getPcMenos());
        existingSeparation.setPcErrada(updatedSeparation.getPcErrada());
        existingSeparation.setErrorPcMais(updatedSeparation.getErrorPcMais());
        existingSeparation.setErrorPcMenos(updatedSeparation.getErrorPcMenos());
        existingSeparation.setErrorPcErrada(updatedSeparation.getErrorPcErrada());

        // Atualizar a soma acumulada de erros
        existingSeparation.setSubTotPcMais(updatedSeparation.getSubTotPcMais());
        existingSeparation.setSubTotPcMenos(updatedSeparation.getSubTotPcMenos());
        existingSeparation.setSubTotPcErrada(updatedSeparation.getSubTotPcErrada());
        // Salvar a separação atualizada no banco de dados
        Separation updatedSeparationEntity = separationRepository.save(existingSeparation);

        return updatedSeparationEntity;
    }
    */

    public void updateAccumulatedSumOfErrors(Separation separation) {
        Long separationId = separation.getId();

        // Busque os valores do banco de dados antes de somar
        Integer pcMaisFromDB = separationRepository.findPcMaisById(separationId);
        Integer errorPcMaisFromDB = separationRepository.findErrorPcMaisById(separationId);

        Integer pcMenosFromDB = separationRepository.findPcMaisById(separationId);
        Integer errorPcMenosFromDB = separationRepository.findErrorPcMaisById(separationId);

        Integer pcErradaFromDB = separationRepository.findPcMaisById(separationId);
        Integer errorPcErradaFromDB = separationRepository.findErrorPcMaisById(separationId);

        // Some os valores do banco com os valores existentes na entidade Separation
        int sumPcMais = (pcMaisFromDB != null ? pcMaisFromDB : 0) + (separation.getPcMais() != null ? separation.getPcMais() : 0);
        int sumErrorPcMais = (errorPcMaisFromDB != null ? errorPcMaisFromDB : 0) + (separation.getErrorPcMais() != null ? separation.getErrorPcMais() : 0);

        int sumPcMenos = (pcMenosFromDB != null ? pcMenosFromDB : 0) + (separation.getPcMenos() != null ? separation.getPcMenos() : 0);
        int sumErrorPcMenos = (errorPcMenosFromDB != null ? errorPcMenosFromDB : 0) + (separation.getErrorPcMenos() != null ? separation.getErrorPcMenos() : 0);

        int sumPcErrada = (pcMaisFromDB != null ? pcErradaFromDB : 0) + (separation.getPcErrada() != null ? separation.getPcErrada() : 0);
        int sumErrorPcErrada = (errorPcErradaFromDB != null ? errorPcErradaFromDB : 0) + (separation.getErrorPcErrada() != null ? separation.getErrorPcErrada() : 0);

        // Atualize os campos na entidade Separation
        separation.setSubTotPcMais(sumPcMais);
        separation.setErrorPcMais(sumErrorPcMais);
        separation.setSubTotPcMenos(sumPcMenos);
        separation.setErrorPcMenos(sumErrorPcMenos);
        separation.setSubTotPcErrada(sumPcErrada);
        separation.setErrorPcErrada(sumErrorPcErrada);

        // Salve a separação atualizada no banco de dados
        separationRepository.save(separation);
    }

    public Separation updateErrors(Long id, SeparationRequestDTO errorData) {
        return separationRepository.getReferenceById(id);
    }
}





