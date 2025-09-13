package com.backend.com.backend.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChartData {

    private String label;
    private Double value;

    public ChartData() {
    }

    public ChartData(String label, Double value) {
        this.label = label;
        this.value = value;
    }

}