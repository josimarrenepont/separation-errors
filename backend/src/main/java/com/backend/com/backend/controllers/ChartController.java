package com.backend.com.backend.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.com.backend.entities.ChartData;

@RestController
@RequestMapping("/SeparationForm")
public class ChartController {

    @GetMapping("/chart-data")
    public ResponseEntity<List<ChartData>> getChartData() {
        List<ChartData> data = new ArrayList<>();

        data.add(new ChartData("Ativo 1", 1000.0));
        data.add(new ChartData("Ativo 2", 1500.0));
        data.add(new ChartData("Ativo 3", 800.0));

        return ResponseEntity.ok(data);
    }
}
