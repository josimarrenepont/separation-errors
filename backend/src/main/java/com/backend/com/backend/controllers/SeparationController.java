package com.backend.com.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.services.SeparationSeervice;

@RestController
@RequestMapping(value = "/separations")
public class SeparationController {

    @Autowired
    private SeparationSeervice service;

    public ResponseEntity<List<Separation>> findAll() {
        List<Separation> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }
}