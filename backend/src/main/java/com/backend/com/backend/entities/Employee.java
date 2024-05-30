package com.backend.com.backend.entities;

import com.backend.com.backend.entities.dto.SeparationRequestDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Entity
@Table(name = "tb_employee")
public class Employee implements Serializable {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private String branch;
    @Setter
    @Getter
    private Integer totPcMais;
    @Setter
    @Getter
    private Integer totPcMenos;
    @Setter
    @Getter
    private Integer totPcErrada;


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
