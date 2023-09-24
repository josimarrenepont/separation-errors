package com.backend.com.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.backend.com.backend.services.SeparationService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/separations")
public class SeparationController {

    @Autowired
    private SeparationService service;

    @GetMapping
    public ResponseEntity<List<Separation>> findAll() {
        List<Separation> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        Separation obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping("/separations") // Rota para salvar separação
    public ResponseEntity<Separation> saveSeparation(@RequestBody Separation separation) {
        Separation savedSeparation = service.save(separation);
        return ResponseEntity.ok(savedSeparation);
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleOptions() {
        return ResponseEntity.ok().build();
    }
}
