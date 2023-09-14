package com.backend.com.backend.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_employee")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String branch;
    private Integer totPcMais;
    private Integer totPcMenos;
    private Integer totPcErrada;

    public Employee() {
    }

    public Employee(Long id, String name, String branch, Integer totPcMais, Integer totPcMenos, Integer totPcErrada) {
        this.id = id;
        this.name = name;
        this.branch = branch;
        this.totPcMais = totPcMais;
        this.totPcMenos = totPcMenos;
        this.totPcErrada = totPcErrada;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Integer getTotPcMais() {
        return totPcMais;
    }

    public void setTotPcMais(Integer totPcMais) {
        this.totPcMais = totPcMais;
    }

    public Integer getTotPcMenos() {
        return totPcMenos;
    }

    public void setTotPcMenos(Integer totPcMenos) {
        this.totPcMenos = totPcMenos;
    }

    public Integer getTotPcErrada() {
        return totPcErrada;
    }

    public void setTotPcErrada(Integer totPcErrada) {
        this.totPcErrada = totPcErrada;
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
        Employee other = (Employee) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}