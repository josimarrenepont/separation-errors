package com.backend.com.backend.controllers;

import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.entities.SeparationErrorHistory;
import com.backend.com.backend.entities.dto.SeparationRequestDTO;
import com.backend.com.backend.repositories.SeparationRepository;
import com.backend.com.backend.services.EmployeeService;
import com.backend.com.backend.services.SeparationService;
import com.backend.com.backend.services.exceptions.ResourceNotFoundException;
import com.backend.com.backend.services.impl.EmployeeServiceImpl;
import com.backend.com.backend.services.impl.SeparationServiceImpl;
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


    private final SeparationService separationService;
    private final EmployeeService employeeService;
    private final SeparationRepository separationRepository;

    public SeparationController(SeparationServiceImpl separationService,
                                EmployeeServiceImpl employeeService,
                                SeparationRepository separationRepository) {
        this.separationService = separationService;
        this.employeeService = employeeService;
        this.separationRepository = separationRepository;
    }

    @GetMapping
    public ResponseEntity<List<Separation>> findAll() {
        List<Separation> list = separationService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        Separation obj = separationService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Separation> updateSeparationErrors(
            @PathVariable Long id, @RequestBody Separation errorData) {
        Separation updateSeparation = separationService.updateErrors(id, errorData);
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
        Separation newSeparation = separationService.createSeparation(separation);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSeparation);
    }

    @PutMapping("/update-error")
    @Transactional
    public ResponseEntity<?> updateSeparationError(@RequestBody Separation errorDataDTO) {
        try {
            Separation separations = separationRepository.findById(errorDataDTO.getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Separação não encontrada"));

            SeparationErrorHistory history = new SeparationErrorHistory();
            history.setName(separations.getName());
            history.setDate(new Date());
            history.setCodProduct(separations.getCodProduct());
            history.setPallet(separations.getPallet());
            history.setErrorPcMais(separations.getErrorPcMais());
            history.setErrorPcMenos(separations.getErrorPcMenos());
            history.setErrorPcErrada(separations.getErrorPcErrada());

            separations.addErrorHistory(history);

            separations.setErrorPcMais(errorDataDTO.getErrorPcMais());
            separations.setErrorPcMenos(errorDataDTO.getErrorPcMenos());
            separations.setErrorPcErrada(errorDataDTO.getErrorPcErrada());
            
            separationRepository.save(separations);

            return ResponseEntity.ok("Separation updated successfully with error history");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating separation: " + e.getMessage());
        }
    }

    private static SeparationErrorHistory getSeparationErrorHistory(Separation separation, Separation separations) {
        SeparationErrorHistory errorHistory = new SeparationErrorHistory();
        errorHistory.setName(separation.getName());
        errorHistory.setId(separations.getId());
        errorHistory.setDate(new Date());
        errorHistory.setCodProduct(errorHistory.getCodProduct());
        errorHistory.setPallet(errorHistory.getPallet());
        errorHistory.setErrorPcMais(errorHistory.getErrorPcMais());
        errorHistory.setErrorPcMenos(errorHistory.getErrorPcMenos());
        errorHistory.setErrorPcErrada(errorHistory.getErrorPcErrada());
        return errorHistory;
    }

    @PutMapping("/separations/updateErrors/{id}")
    public ResponseEntity<Separation> updateSeparationErrors(
            @PathVariable Long id, @RequestBody SeparationRequestDTO errorData) {
        Separation updateSeparation = separationService.updateErrors(id, errorData);

        return ResponseEntity.ok(updateSeparation);
    }
}

