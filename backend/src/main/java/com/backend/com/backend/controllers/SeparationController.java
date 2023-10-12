package com.backend.com.backend.controllers;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.com.backend.entities.Employee;
import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.entities.dto.SeparationRequestDTO;
import com.backend.com.backend.services.EmployeeService;
import com.backend.com.backend.services.SeparationService;
import com.backend.com.backend.services.exceptions.ResourceNotFoundException;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/separations")
public class SeparationController {

    @Autowired
    private SeparationService separationService;

    @Autowired
    private EmployeeService employeeService;

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

    @PostMapping("/updateErrors/{employeeId}")
    public ResponseEntity<Separation> updateErrors(@PathVariable Long employeeId,
            @RequestBody Employee errorData) {

        Separation separation = separationService.updateErrors(employeeId, errorData);
        return ResponseEntity.ok(separation);
    }

    public ResponseEntity<List<SeparationRequestDTO>> getSeparations() {
        List<Separation> separations = separationService.getAllSeparations();

        // Use um SimpleDateFormat para formatar as datas antes de enviá-las para o
        // front-end
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        List<SeparationRequestDTO> separationDTOs = separations.stream().map(separation -> {
            SeparationRequestDTO dto = new SeparationRequestDTO();
            dto.setDate(sdf.format(separation.getDate())); // Formate a data aqui
            dto.setId(separation.getId());
            dto.setName(separation.getName());
            dto.setPallet(separation.getPallet());
            dto.setCodProduct(separation.getCodProduct());
            dto.setErrorPcMais(separation.getErrorPcMais());
            dto.setErrorPcMenos(separation.getErrorPcMenos());
            dto.setErrorPcErrada(separation.getErrorPcErrada());
            dto.setPcMais(separation.getPcMais());
            dto.setPcMenos(separation.getPcMenos());
            dto.setPcErrada(separation.getPcErrada());
            return dto;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(separationDTOs, HttpStatus.OK);
    }

    @PostMapping("/separations")
    public ResponseEntity<Separation> addError(@RequestBody SeparationRequestDTO errorData) {
        Separation newError = separationService.addError(errorData);
        return ResponseEntity.ok(newError);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updatedEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployeeData) {
        try {
            Separation updatedEmployee = separationService.updateErrors(id, updatedEmployeeData);
            return ResponseEntity.ok(updatedEmployeeData);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/addErrorToEmployee")
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

}
