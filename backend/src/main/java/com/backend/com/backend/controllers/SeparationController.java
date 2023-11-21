package com.backend.com.backend.controllers;

import com.backend.com.backend.entities.Employee;
import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.entities.SeparationErrorHistory;
import com.backend.com.backend.entities.dto.SeparationRequestDTO;
import com.backend.com.backend.repositories.SeparationRepository;
import com.backend.com.backend.services.EmployeeService;
import com.backend.com.backend.services.SeparationService;
import com.backend.com.backend.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping(value = "/separations")
public class SeparationController {

    @Autowired
    private final SeparationService separationService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SeparationRepository separationRepository;

    @Autowired
    public SeparationController(SeparationService separationService) {
        this.separationService = separationService;
    }

    @GetMapping
    public ResponseEntity<List<Separation>> findAll() {
        List<Separation> list = separationService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PutMapping(value = "/updateErrorsAndSums")
    public ResponseEntity<String> updateErrorsAndSums(@RequestBody Separation separation) {
        // Lógica para realizar a ação necessária com os erros ou separações

        // Após realizar a ação, chame o serviço para atualizar as somas acumuladas
        separationService.updateAccumulatedSumsOfErrors();

        return ResponseEntity.ok("Erros e somas acumuladas atualizadas com sucesso");
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        Separation obj = separationService.findById(id);
        return ResponseEntity.ok().body(obj);
    }
    @PutMapping(value = "/separations")
    public ResponseEntity<SeparationRequestDTO> addEmployeeToSeparation(
            @RequestBody Long separationId,
            @RequestBody Long employeeId
    ) {
        Separation separation = separationService.getSeparationById(separationId);
        Employee employee = employeeService.getEmployeeById(employeeId);

        if (separation == null || employee == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Adicione o funcionário ao registro de separação.
        separation.addEmployee(employee);

        // Salve o registro de separação atualizado no serviço.
        Separation updatedSeparation = separationService.updateSeparation(separation);

        // Converta a separação atualizada em um DTO para retorná-la ao cliente.
        SeparationRequestDTO separationDTO = new SeparationRequestDTO();
        separationDTO.setId(updatedSeparation.getId());
        separationDTO.setName(updatedSeparation.getName());
        separationDTO.setDate(updatedSeparation.getDate());
        separationDTO.setCodProduct(updatedSeparation.getCodProduct());
        separationDTO.setPallet(updatedSeparation.getPallet());
        separationDTO.setErrorPcMais(updatedSeparation.getErrorPcMais());
        separationDTO.setErrorPcMenos(updatedSeparation.getErrorPcMenos());
        separationDTO.setErrorPcErrada(updatedSeparation.getErrorPcErrada());
        separationDTO.setPcMais(updatedSeparation.getPcMais());
        separationDTO.setPcMenos(updatedSeparation.getPcMenos());
        separationDTO.setPcErrada(updatedSeparation.getPcErrada());
        // Copie outros campos para o DTO conforme necessário.

        return new ResponseEntity<>(separationDTO, HttpStatus.OK);
    }

    public ResponseEntity<Separation> updateSeparationErrors(
            @PathVariable Long separationId, @RequestBody Separation errorData){
        Separation updateSeparation = separationService.updateErrors(separationId, errorData);
        return ResponseEntity.ok(updateSeparation);
    }

    @PutMapping("/separationRequestDTO")
    public ResponseEntity<Separation> addError(@RequestBody Separation errorData) {
        Separation newError = separationService.addError(errorData);
        return ResponseEntity.ok(newError);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Separation> updatedEmployee(@PathVariable Long id, @RequestBody Separation updatedEmployeeData) {
        try {
            separationService.updateErrors(id, updatedEmployeeData);
            return ResponseEntity.ok(updatedEmployeeData);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Separation> createSeparation(@RequestBody Separation separation) {
        Separation newSeparation = separationService.createSeparation(separation); // Implemente createSeparation no seu serviço
        return ResponseEntity.status(HttpStatus.CREATED).body(newSeparation);
    }


    public ResponseEntity<Separation> updateSeparation(@RequestBody Separation updatedSeparation) {
        Separation updated = separationService.updateSeparation(updatedSeparation);
        return ResponseEntity.ok(updatedSeparation);
    }
    @PutMapping
    @Transactional
    public ResponseEntity<?> updateSeparationError(@RequestBody Separation separation) {
        try {
            // Lógica para atualizar a separação em tb_separation
            Separation separations = new Separation();
            separations.setName(separation.getName());
            separations.setId(separations.getId());
            separations.setPcMais(separations.getPcMais());
            separations.setErrorPcMais(separations.getErrorPcMais());
            separations.setPcMenos(separations.getPcMenos());
            separations.setErrorPcMenos(separations.getErrorPcMenos());
            separations.setPcErrada(separations.getPcErrada());
            separations.setErrorPcErrada(separations.getErrorPcErrada());
            separations.setSubTotPcMais(separations.getSubTotPcMais());
            separations.setSubTotPcMenos(separations.getSubTotPcMenos());
            separations.setSubTotPcErrada(separations.getSubTotPcErrada());

            // Criar um novo histórico de erro
            SeparationErrorHistory errorHistory = new SeparationErrorHistory();
            errorHistory.setName(separation.getName());
            errorHistory.setId(separations.getId());
            errorHistory.setDate(new Date());
            errorHistory.setCodProduct(errorHistory.getCodProduct());
            errorHistory.setPallet(errorHistory.getPallet());
            errorHistory.setPcMais(errorHistory.getPcMais());
            errorHistory.setPcMenos(errorHistory.getPcMenos());
            errorHistory.setPcErrada(errorHistory.getPcErrada());
            errorHistory.setErrorPcMais(errorHistory.getErrorPcMais());
            errorHistory.setErrorPcMenos(errorHistory.getErrorPcMenos());
            errorHistory.setErrorPcErrada(errorHistory.getErrorPcErrada());
            // Definir outros campos do histórico de erro conforme necessário

            // Adicionar o histórico de erro à separação
            separation.addErrorHistory(errorHistory);

            // Salvar a separação (que agora inclui o histórico) no banco de dados
            separationRepository.save(separation);

            return ResponseEntity.ok("Separation updated successfully with error history");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating separation: " + e.getMessage());
        }

    }
    @PutMapping("/separations/updateErrors/{id}")
    public ResponseEntity<Separation> updateSeparationErrors(
            @PathVariable Long id, @RequestBody SeparationRequestDTO errorData) {
        Separation updateSeparation = separationService.updateErrors(id, errorData);

        // Atualize as somas acumuladas após adicionar erros

        return ResponseEntity.ok(updateSeparation);
    }


}


