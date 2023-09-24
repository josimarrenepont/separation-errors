package com.backend.com.backend.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_separation")
public class Separation implements Serializable {

    private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Date date;
    private String name;
    private Integer codProduct;
    private Integer pallet;
    private Integer pcMais;
    private Integer pcMenos;
    private Integer pcErrada;
    private Integer error;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "employee_separation", joinColumns = @JoinColumn(name = "separation.id"), inverseJoinColumns = @JoinColumn(name = "employee.id"))
    private Set<Employee> employees = new HashSet<>();

    public Separation() {
    }

    public Separation(Long id, Date date, String name, Integer codProduct, Integer pallet, Integer pcMais,
            Integer pcMenos, Integer pcErrada, Integer error) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.codProduct = codProduct;
        this.pallet = pallet;
        this.pcMais = pcMais;
        this.pcMenos = pcMenos;
        this.pcErrada = pcErrada;
        this.error = error;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCodProduct() {
        return codProduct;
    }

    public void setCodProduct(Integer codProduct) {
        this.codProduct = codProduct;
    }

    public Integer getPallet() {
        return pallet;
    }

    public void setPallet(Integer pallet) {
        this.pallet = pallet;
    }

    public Integer getPcMais() {
        return pcMais;
    }

    public void setPcMais(Integer pcMais) {
        this.pcMais = pcMais;
    }

    public Integer getPcMenos() {
        return pcMenos;
    }

    public void setPcMenos(Integer pcMenos) {
        this.pcMenos = pcMenos;
    }

    public Integer getPcErrada() {
        return pcErrada;
    }

    public void setPcErrada(Integer pcErrada) {
        this.pcErrada = pcErrada;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public Set<Employee> getErrors() {
        return employees;
    }

    public Integer getSubTotPcMais() {
        return pcMais + error;
    }

    public Integer getSubTotPcMenos() {
        return pcMenos + error;
    }

    public Integer getSubTotPcErrada() {
        return pcErrada + error;
    }

    public void addErrorTotPcMais(int selectedPcMais) {
        pcMais += selectedPcMais;
    }

    public void addErrorTotPcMenos(int selectedPcMenos) {
        pcMenos += selectedPcMenos;
    }

    public void addErrorTotPcErrada(int selectedPcErrada) {
        pcErrada += selectedPcErrada;
    }

    public Integer TotPcMais(Integer pcMais) {
    	return getSubTotPcMais();
    }
    public Integer TotPcMenos(Integer pcMenos) {
    	return getSubTotPcMenos();
    }
    
    public Integer TotPcErrada(Integer pcErrada) {
    	return getPcErrada();
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Separation other = (Separation) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}