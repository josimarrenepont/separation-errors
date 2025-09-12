package com.backend.com.backend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "America/Sao_Paulo")
    private Date date;
    @Getter
    private String name;
    @Getter
    private Integer codProduct;
    @Getter
    private Integer pallet;
    @Getter
    private Integer errorPcMais;
    @Getter
    private Integer errorPcMenos;
    @Getter
    private Integer errorPcErrada;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "employee_separation", joinColumns = @JoinColumn(name = "separation_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private Set<Employee> employees = new HashSet<>();

    @Setter
    @Getter
    @ManyToMany(mappedBy = "separation", cascade = CascadeType.ALL)
    private Set<SeparationErrorHistory> errorHistory = new HashSet<>();


    public Separation() {
    }

    public Separation(Long id, Date date, String name, Integer codProduct, Integer pallet,
                      Integer errorPcMais, Integer errorPcMenos, Integer errorPcErrada) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.codProduct = codProduct;
        this.pallet = pallet;
        this.errorPcMais = (errorPcMais != null) ? errorPcMais : 0;
        this.errorPcMenos = (errorPcMenos != null) ? errorPcMenos : 0;
        this.errorPcErrada = (errorPcErrada != null) ? errorPcErrada : 0;

    }
    public void setId(Long id) {this.id = id;}

    public void setDate(Date date) {this.date = date;}

    public void setName(String name) {
        this.name = name;
    }

    public void setPallet(Integer pallet) {
        this.pallet = pallet;
    }

    public void setCodProduct(Integer codProduct) {
        this.codProduct = codProduct;
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

    public void addErrorHistory(SeparationErrorHistory separationErrorHistory) {
        if (this.errorHistory == null) {
            this.errorHistory = getErrorHistory();
        }
        this.errorHistory.add(separationErrorHistory);
        separationErrorHistory.setSeparation(this);
    }
    public void addErrorToHistory(SeparationErrorHistory errorHistory) {
        this.errorHistory.add(errorHistory);

    }

    public void addEmployee(Employee existingEmployee) {
        employees.add(existingEmployee);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Separation that))
            return false;
        return Objects.equals(getId(), that.getId());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}