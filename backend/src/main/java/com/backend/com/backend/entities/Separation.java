package com.backend.com.backend.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Separation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Date date;
    private String name;
    private Integer codProduct;
    private Integer pallet;
    private Integer pcMais;
    private Integer pcMenos;
    private Integer pcErrada;
    private Integer errorPcMais;
    private Integer errorPcMenos;
    private Integer errorPcErrada;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "employee_separation", joinColumns = @JoinColumn(name = "separation.id"), inverseJoinColumns = @JoinColumn(name = "employee.id"))
    private Set<Employee> employees = new HashSet<>();

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

    public Integer getErrorPcMais() {
        return errorPcMais;
    }

    public void setErrorPcMais(Integer errorPcMais) {
        this.errorPcMais = errorPcMais;
    }

    public Integer getErrorPcMenos() {
        return errorPcMenos;
    }

    public void setErrorPcMenos(Integer errorPcMenos) {
        this.errorPcMenos = errorPcMenos;
    }

    public Integer getErrorPcErrada() {
        return errorPcErrada;
    }

    public void setErrorPcErrada(Integer errorPcErrada) {
        this.errorPcErrada = errorPcErrada;
    }

    public Set<Employee> getErrors() {
        return employees;
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

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
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
        return Objects.equals(id, other.id) && Objects.equals(name, other.name);
    }


}