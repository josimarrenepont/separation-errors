package com.backend.com.backend.controllers;

import com.backend.com.backend.entities.SeparationErrorHistory;
import com.backend.com.backend.services.SeparationErrorHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost/5173")
@RequestMapping("/separation-error-history")
public class SeparationErrorHistoryController {

    @Autowired
    private SeparationErrorHistoryService errorHistoryService;

    @GetMapping
    public ResponseEntity<List<SeparationErrorHistory>> findAll(){
        List<SeparationErrorHistory> list = errorHistoryService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id){
        SeparationErrorHistory obj = errorHistoryService.findById(id);
        return ResponseEntity.ok().body(obj);
    }
    @PutMapping
    public SeparationErrorHistory createErrorHistory(@RequestBody SeparationErrorHistory errorHistory){
    return errorHistoryService.createErrorHistory(errorHistory);
    }

    //@GetMapping("/{id}")
    //public SeparationErrorHistory getErrorHistoryByld(@PathVariable Long id){
      //  return errorHistoryService.getErrorHistoryByld(id);
   // }
    //@GetMapping("/all")
   // public List<SeparationErrorHistory> getAllErrorHistory(){
     //   return errorHistoryService.getAllErrorHistory();
    //}


}
