package com.backend.com.backend.controllers;

import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.entities.SeparationErrorHistory;
import com.backend.com.backend.entities.dto.SeparationRequestDTO;
import com.backend.com.backend.repositories.SeparationRepository;
import com.backend.com.backend.services.impl.EmployeeServiceImpl;
import com.backend.com.backend.services.impl.SeparationServiceImpl;
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
    private final SeparationServiceImpl separationServiceImpl;

    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;

    @Autowired
    private SeparationRepository separationRepository;

    @Autowired
    public SeparationController(SeparationServiceImpl separationServiceImpl) {
        this.separationServiceImpl = separationServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<Separation>> findAll() {
        List<Separation> list = separationServiceImpl.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        Separation obj = separationServiceImpl.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Separation> updateSeparationErrors(
            @PathVariable Long id, @RequestBody Separation errorData) {
        Separation updateSeparation = separationServiceImpl.updateErrors(id, errorData);
        return ResponseEntity.ok(updateSeparation);
    }

    @PutMapping("/separationRequestDTO")
    public ResponseEntity<Separation> addError(@RequestBody Separation errorData) {
        Separation newError = separationServiceImpl.addError(errorData);
        return ResponseEntity.ok(newError);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Separation> updatedEmployee(@PathVariable Long id, @RequestBody Separation updatedEmployeeData) {
        try {
            separationServiceImpl.updateErrors(id, updatedEmployeeData);
            return ResponseEntity.ok(updatedEmployeeData);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Separation> createSeparation(@RequestBody Separation separation) {
        Separation newSeparation = separationServiceImpl.createSeparation(separation);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSeparation);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> updateSeparationError(@RequestBody Separation separation) {
        try {
            // Atualizar a separação em tb_separation
            Separation separations = new Separation();
            separations.setName(separations.getName());
            separations.setId(separations.getId());
            separations.setErrorPcMais(separations.getErrorPcMais());
            separations.setErrorPcMenos(separations.getErrorPcMenos());
            separations.setErrorPcErrada(separations.getErrorPcErrada());
            ;

            // Criar um novo histórico de erro
            SeparationErrorHistory errorHistory = new SeparationErrorHistory();
            errorHistory.setName(separation.getName());
            errorHistory.setId(separations.getId());
            errorHistory.setDate(new Date());
            errorHistory.setCodProduct(errorHistory.getCodProduct());
            errorHistory.setPallet(errorHistory.getPallet());
            errorHistory.setErrorPcMais(errorHistory.getErrorPcMais());
            errorHistory.setErrorPcMenos(errorHistory.getErrorPcMenos());
            errorHistory.setErrorPcErrada(errorHistory.getErrorPcErrada());

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
        Separation updateSeparation = separationServiceImpl.updateErrors(id, errorData);

        return ResponseEntity.ok(updateSeparation);
    }
}

