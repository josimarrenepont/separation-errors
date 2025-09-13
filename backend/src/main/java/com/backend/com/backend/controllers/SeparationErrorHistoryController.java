package com.backend.com.backend.controllers;

import com.backend.com.backend.entities.SeparationErrorHistory;
import com.backend.com.backend.services.impl.SeparationErrorHistoryServiceImpl;
import com.backend.com.backend.services.impl.SeparationServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/separation-error-history")
public class SeparationErrorHistoryController {


    private final SeparationErrorHistoryServiceImpl errorHistoryService;
    private final SeparationServiceImpl separationServiceImpl;

    public SeparationErrorHistoryController(SeparationErrorHistoryServiceImpl errorHistoryService,
                                            SeparationServiceImpl separationServiceImpl) {
        this.errorHistoryService = errorHistoryService;
        this.separationServiceImpl = separationServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<SeparationErrorHistory>> findAll(){
        List<SeparationErrorHistory> list = errorHistoryService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/error-history/{codProduct}")
    public ResponseEntity<List<SeparationErrorHistory>> getErrorHistoryByCodProduct(@PathVariable Integer codProduct) {
        List<SeparationErrorHistory> errorHistory = errorHistoryService.getErrorHistoryByCodProduct(codProduct);
        return new ResponseEntity<>(errorHistory, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id){
        SeparationErrorHistory obj = errorHistoryService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

}
