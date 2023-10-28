package com.backend.com.backend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "tb_separation")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Separation implements Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Date date;
    @Getter
    private String name;
    @Getter
    private Integer codProduct;
    @Getter
    private Integer pallet;
    @Getter
    private Integer pcMais;
    @Getter
    private Integer pcMenos;
    @Getter
    private Integer pcErrada;
    @Getter
    private Integer errorPcMais;
    @Getter
    private Integer errorPcMenos;
    @Getter
    private Integer errorPcErrada;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "employee_separation", joinColumns = @JoinColumn(name = "separation_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private Set<Employee> employees = new HashSet<>();

    @Getter
    @OneToMany(mappedBy = "separation", cascade = CascadeType.ALL)
    private Set<SeparationErrorHistory> errorHistory = new HashSet<>();

    public Separation() {
    }

    public Separation(Long id, Date date, String name, Integer codProduct, Integer pallet, Integer pcMais,
            Integer pcMenos, Integer pcErrada, Integer errorPcMais, Integer errorPcMenos, Integer errorPcErrada) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.codProduct = codProduct;
        this.pallet = pallet;
        this.pcMais = (pcMais != null) ? pcMais : 0;
        this.pcMenos = (pcMenos != null) ? pcMenos : 0;
        this.pcErrada = (pcErrada != null) ? pcErrada : 0;
        this.errorPcMais = (errorPcMais != null) ? errorPcMais : 0;
        this.errorPcMenos = (errorPcMenos != null) ? errorPcMenos : 0;
        this.errorPcErrada = (errorPcErrada != null) ? errorPcErrada : 0;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setErrorPcMenos(Integer errorPcMenos) {
        this.errorPcMenos = errorPcMenos;
    }

    public void setErrorPcErrada(Integer errorPcErrada) {
        this.errorPcErrada = errorPcErrada;
    }

    public Set<Employee> getErrors() {
        return employees;
    }

    public void addErrorToHistory(SeparationErrorHistory errorHistoryEntry){
        this.errorHistory.add(errorHistoryEntry);
    }

    public Integer getSubTotPcMais() {
        if (pcMais == null) {
            return 0; // ou retorne null se preferir
        }
        return pcMais + errorPcMais;
    }

    public Integer getSubTotPcMenos() {
        if (pcMenos == null) {
            return 0; // ou retorne null se preferir
        }
        return pcMenos + errorPcMenos;
    }

    public Integer getSubTotPcErrada() {
        if (pcErrada == null) {
            return 0; // ou retorne null se preferir
        }
        return pcErrada + errorPcErrada;
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

    public void addEmployee(Employee existingEmployee) {
        employees.add(existingEmployee);
    }

    public Employee[] getEmployees() {
        return employees.toArray(new Employee[0]);
    }
    public void setEmployee(Employee employee) {
        addEmployee(employee);
    }

    public void setErrorHistory(Set<SeparationErrorHistory> errorHistory) {
        this.errorHistory = errorHistory;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Separation that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


}