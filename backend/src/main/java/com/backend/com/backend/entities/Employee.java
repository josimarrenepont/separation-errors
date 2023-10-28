package com.backend.com.backend.entities;

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
    private String name;
    private String branch;


    @ManyToMany(mappedBy = "employees")
    private Set<Separation> errors = new HashSet<>();


    public Employee() {
    }

    public Employee(Long id, String name, String branch) {
        this.id = id;
        this.name = name;
        this.branch = branch;


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

    public Integer getTotPcMais() {
        int sumPcMais = 0;
        for (Separation error : errors) {
            sumPcMais += error.getSubTotPcMais();
        }
        return sumPcMais;
    }

    public Integer getTotPcMenos() {
        int sumPcMenos = 0;
        for (Separation error : errors) {
            sumPcMenos += error.getSubTotPcMenos();
        }
        return sumPcMenos;
    }

    public Integer getTotPcErrada() {
        int sumPcErrada = 0;
        for (Separation error : errors) {
            sumPcErrada += error.getSubTotPcErrada();
        }
        return sumPcErrada;
    }

    public void addError(Separation errorData) {
        addError(errorData);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        return Objects.equals(getId(), employee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
