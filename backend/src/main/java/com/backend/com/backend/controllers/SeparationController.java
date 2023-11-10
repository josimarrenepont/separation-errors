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

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping(value = "/separations")
public class SeparationController {

    @Autowired
    private SeparationService separationService;

    @Autowired
    private EmployeeService employeeService;


    @Autowired
    public SeparationController(SeparationService separationService) {
        this.separationService = separationService;
    }



    public void setSeparationService(SeparationService separationService) {
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


    @PutMapping("/separations")
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
            @PathVariable Long separationId, @RequestBody SeparationRequestDTO errorData){
        Separation updateSeparation = separationService.updateErrors(separationId, errorData);
        return ResponseEntity.ok(updateSeparation);
    }



    @PutMapping("/separationRequestDTO")
    public ResponseEntity<Separation> addError(@RequestBody SeparationRequestDTO errorData) {
        Separation newError = separationService.addError(errorData);
        return ResponseEntity.ok(newError);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<SeparationRequestDTO> updatedEmployee(@PathVariable Long id, @RequestBody SeparationRequestDTO updatedEmployeeData) {
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
    @PutMapping
    public ResponseEntity<Separation> updateSeparation(@RequestBody Separation updatedSeparation) {
        Separation updated = separationService.updateSeparation(updatedSeparation);
        System.out.println("Dados recebidos para atualização: " + updatedSeparation.toString());
        return ResponseEntity.ok(updatedSeparation);
    }


}


