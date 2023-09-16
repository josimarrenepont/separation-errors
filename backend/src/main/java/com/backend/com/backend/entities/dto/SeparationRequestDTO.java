package com.backend.com.backend.entities.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.backend.com.backend.entities.Separation;

public class SeparationRequestDTO {

    private Long id;
    private Date date;
    private String employee;
    private Integer pcMais;
    private Integer pcMenos;
    private Integer pcErrada;
    private Integer palette;
    private Integer product;
    private Integer error;

    public SeparationRequestDTO() {
    }

    public SeparationRequestDTO(Separation entity) {
        BeanUtils.copyProperties(entity, this);
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
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

}