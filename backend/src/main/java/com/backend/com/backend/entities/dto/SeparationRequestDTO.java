package com.backend.com.backend.entities.dto;

import org.springframework.beans.BeanUtils;

import com.backend.com.backend.entities.Separation;

public class SeparationRequestDTO {

    private Long id;
    private String date;
    private String employee;
    private Integer pcMais;
    private Integer pcMenos;
    private Integer pcErrada;
    private Integer palette;
    private Integer product;
    private Integer error;
    private String name;

    public SeparationRequestDTO() {
    }

    public SeparationRequestDTO(Separation entity) {
        BeanUtils.copyProperties(entity, this);
    }

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getEmployee() {
        return employee;
    }

    public Integer getPcMais() {
        return pcMais;
    }

    public Integer getPcMenos() {
        return pcMenos;
    }

    public Integer getPcErrada() {
        return pcErrada;
    }

    public Integer getPalette() {
        return palette;
    }

    public Integer getProduct() {
        return product;
    }

    public Integer getError() {
        return error;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDate(String date) {
        this.date = date;
    }
}