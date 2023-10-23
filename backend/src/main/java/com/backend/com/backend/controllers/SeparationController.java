package com.backend.com.backend.controllers;

import com.backend.com.backend.entities.Employee;
import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.entities.dto.SeparationRequestDTO;
import com.backend.com.backend.services.EmployeeService;
import com.backend.com.backend.services.SeparationService;
import com.backend.com.backend.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/separations")
public class SeparationController {

    @Autowired
    private SeparationService separationService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    public SeparationController(SeparationService separationService) {
        this.separationService = separationService;
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


    @PutMapping("/separations/{id}")
    public ResponseEntity<SeparationRequestDTO> updateSeparation(@PathVariable Long id, @RequestBody Separation updateSeparation) {
        Separation existingSeparation = separationService.getSeparationById(id);

        if (existingSeparation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Verifique se os campos em 'updateSeparation' não são nulos antes de atualizar
        if (updateSeparation.getDate() != null) {
            existingSeparation.setDate(updateSeparation.getDate());
        }

        if (updateSeparation.getCodProduct() != null) {
            existingSeparation.setCodProduct(updateSeparation.getCodProduct());
        }

        if (updateSeparation.getPallet() != null) {
            existingSeparation.setPallet(updateSeparation.getPallet());
        }

        // Atualize outros campos conforme necessário
        existingSeparation.setName(updateSeparation.getName());
        existingSeparation.setDate(updateSeparation.getDate());
        existingSeparation.setPallet(updateSeparation.getPallet());
        existingSeparation.setId(updateSeparation.getId());
        existingSeparation.setCodProduct(updateSeparation.getCodProduct());
        existingSeparation.setErrorPcMais(updateSeparation.getErrorPcMais());
        existingSeparation.setErrorPcMenos(updateSeparation.getErrorPcMenos());
        existingSeparation.setErrorPcErrada(updateSeparation.getErrorPcErrada());
        existingSeparation.setPcMais(updateSeparation.getPcMais());
        existingSeparation.setPcMenos(updateSeparation.getPcMenos());
        existingSeparation.setPcErrada(updateSeparation.getPcErrada());

        Separation updatedSeparation = separationService.updateSeparation(existingSeparation);

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

        return new ResponseEntity<>(separationDTO, HttpStatus.OK);
    }



    @PutMapping("/separationRequestDTO")
    public ResponseEntity<Separation> addError(@RequestBody SeparationRequestDTO errorData) {
        Separation newError = separationService.addError(errorData);
        return ResponseEntity.ok(newError);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updatedEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployeeData) {
        try {
            separationService.updateErrors(id, updatedEmployeeData);
            return ResponseEntity.ok(updatedEmployeeData);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/addErrorToEmployee")
    public ResponseEntity<Separation> addErrorToEmployee(@RequestBody SeparationRequestDTO errorData) {
        // Buscar employee com base no nome
        Employee employee = employeeService.findByName(errorData.getName());

        if (employee == null) {
            return ResponseEntity.notFound().build(); // employee não encontrado
        }

        // Obter ID do employee
        Long employeeId = employee.getId();

        // Criar o erro em Separation com employee_id

        SeparationRequestDTO newError = new SeparationRequestDTO();
        newError.setDate(errorData.getDate());
        newError.setEmployeeId(employeeId);
        newError.setPallet(errorData.getPallet());
        newError.setCodProduct(errorData.getCodProduct());
        newError.setErrorPcMais(errorData.getErrorPcMais());
        newError.setErrorPcMenos(errorData.getErrorPcMenos());
        newError.setErrorPcErrada(errorData.getErrorPcErrada());
        newError.setPcMais(errorData.getPcMais());
        newError.setPcMenos(errorData.getPcMenos());
        newError.setPcErrada(errorData.getPcErrada());

        Separation savedError = separationService.addError(newError);

        return ResponseEntity.ok(savedError);
    }
    @PutMapping
    public ResponseEntity<Separation> createSeparation(@RequestBody Separation separation) {
        Separation newSeparation = separationService.createSeparation(separation); // Implemente createSeparation no seu serviço
        return ResponseEntity.status(HttpStatus.CREATED).body(newSeparation);
    }



}
