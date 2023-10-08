package com.backend.com.backend.controllers;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.entities.dto.SeparationRequestDTO;
import com.backend.com.backend.services.SeparationService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/separations")
public class SeparationController {

    @Autowired
    private SeparationService separationService;

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
            @RequestBody Map<String, Integer> errorData) {

        Separation separation = separationService.updateErrors(employeeId, errorData);
        return ResponseEntity.ok(separation);
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleOptions() {
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<List<SeparationRequestDTO>> getSeparations() {
        List<Separation> separations = separationService.getAllSeparations();

        // Use um SimpleDateFormat para formatar as datas antes de enviá-las para o
        // front-end
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        List<SeparationRequestDTO> separationDTOs = separations.stream().map(separation -> {
            SeparationRequestDTO dto = new SeparationRequestDTO();
            dto.setId(separation.getId());
            dto.setName(separation.getName());
            dto.setDate(sdf.format(separation.getDate())); // Formate a data aqui

            return dto;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(separationDTOs, HttpStatus.OK);
    }

    @PostMapping("/separations")
    public ResponseEntity<Separation> addError(@RequestBody SeparationRequestDTO errorData) {
        Separation newError = separationService.addError(errorData);
        return ResponseEntity.ok(newError);
    }

}
