package com.backend.com.backend.controllers;

import com.backend.com.backend.entities.SeparationErrorHistory;
import com.backend.com.backend.services.SeparationErrorHistoryService;
import com.backend.com.backend.services.SeparationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost/5173")
@RequestMapping("/separation-error-history")
public class SeparationErrorHistoryController {

    @Autowired
    private SeparationErrorHistoryService errorHistoryService;

    @Autowired
    private SeparationService separationService;

    @GetMapping
    public ResponseEntity<List<SeparationErrorHistory>> findAll(){
        List<SeparationErrorHistory> list = errorHistoryService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/error-history/{codProduct}")
    public ResponseEntity<List<SeparationErrorHistory>> getErrorHistoryByCodProduct(@PathVariable Integer codProduct) {
        // Lógica para buscar o histórico de erros por código de produto
        List<SeparationErrorHistory> errorHistory = errorHistoryService.getErrorHistoryByCodProduct(codProduct);
        return new ResponseEntity<>(errorHistory, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id){
        SeparationErrorHistory obj = errorHistoryService.findById(id);
        return ResponseEntity.ok().body(obj);
    }
    @GetMapping("/separation-error-history")
    public SeparationErrorHistory createErrorHistory(@RequestBody SeparationErrorHistory errorHistory){
    return errorHistoryService.createErrorHistory(errorHistory);
    }
}
