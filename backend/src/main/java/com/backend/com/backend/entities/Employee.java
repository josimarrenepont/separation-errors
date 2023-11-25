package com.backend.com.backend.entities;

import com.backend.com.backend.entities.dto.SeparationRequestDTO;
import jakarta.persistence.*;
import lombok.Getter;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Entity
@Table(name = "tb_employee")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    private String name;
    @Getter
    private String branch;
    @Getter
    private Integer totPcMais;
    @Getter
    private Integer totPcMenos;
    @Getter
    private Integer totPcErrada;

    private Separation separation;

    @ManyToMany(mappedBy = "employees")
    private Set<Separation> separations = new HashSet<>();

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


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setTotPcMais(Integer totPcMais) {
        this.totPcMais = totPcMais;
    }

    public void setTotPcMenos(Integer totPcMenos) {
        this.totPcMenos = totPcMenos;
    }

    public void setTotPcErrada(Integer totPcErrada) {
        this.totPcErrada = totPcErrada;
    }

    public Integer getTotPcMais() {
        totPcMais = 0;
        for (Separation separations : separations) {
             totPcMais = separations.getErrorPcMais();
        }
        return totPcMais;
    }

    public Integer getTotPcMenos() {
       totPcMenos = 0;
        for (Separation separations : separations) {
            totPcMenos += separations.getErrorPcMenos();
        }
        return totPcMenos;
    }

    public Integer getTotPcErrada() {
        totPcErrada = 0;
        for (Separation separations : separations) {
            totPcErrada += separations.getErrorPcErrada();
        }
        return totPcErrada;
    }
    public void addError(Separation errorData) {

        this.getSeparations().add(errorData);
    }
    public Set<Separation> setErrors(Separation requestDTO){
       SeparationRequestDTO separationRequestDTO = new SeparationRequestDTO();
       return getSeparations();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Employee employee))
            return false;
        return Objects.equals(getId(), employee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
