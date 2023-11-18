package com.backend.com.backend.entities.dto;

import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.entities.SeparationErrorHistory;
import lombok.Getter;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Getter
public class SeparationRequestDTO{

    private Long id;
    private Date date;
    private String name;
    @Getter
    private Integer codProduct;
    @Getter
    private Integer pallet;
    private Integer pcMais;
    private Integer pcMenos;
    private Integer pcErrada;
    private Integer errorPcMais;
    private Integer errorPcMenos;
    private Integer errorPcErrada;
    private Long employeeId;
    @Getter
    private Integer subTotPcMais;
    @Getter
    private Integer subTotPcMenos;
    @Getter
    private  Integer subTotPcErrada;

    public SeparationRequestDTO() {
    }

    public SeparationRequestDTO(Separation entity) {
        BeanUtils.copyProperties(entity, this);
    }

    public void setErrorPcMenos(Integer errorPcMenos) {
        this.errorPcMenos = errorPcMenos;
    }

    public void setErrorPcErrada(Integer errorPcErrada) {
        this.errorPcErrada = errorPcErrada;
    }

    public void setCodProduct(Integer codProduct) {
        this.codProduct = codProduct;
    }

    public void setPallet(Integer pallet) {
        this.pallet = pallet;
    }

    public void setPcMais(Integer pcMais) {
        this.pcMais = pcMais;
    }

    public void setPcMenos(Integer pcMenos) {
        this.pcMenos = pcMenos;
    }

    public void setPcErrada(Integer pcErrada) {
        this.pcErrada = pcErrada;
    }

    public void setErrorPcMais(Integer errorPcMais) {
        this.errorPcMais = errorPcMais;
    }

    public void setSubTotPcMais(Integer subTotPcMais) {
        this.subTotPcMais = subTotPcMais;
    }

    public void setSubTotPcMenos(Integer subTotPcMenos) {
        this.subTotPcMenos = subTotPcMenos;
    }

    public void setSubTotPcErrada(Integer subTotPcErrada) {
        this.subTotPcErrada = subTotPcErrada;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date string) {
        this.date = string;
    }

    public void setDate(String string) {
        this.date = getDate();
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }


    public void setSeparation(Separation separation) {
        separation.setErrorHistory(separation.getErrorHistory());
    }
    public void setSeparationErrosHistory(SeparationErrorHistory errorHistory){
        errorHistory.setSeparation((Separation) errorHistory.getSeparation());
    }
}