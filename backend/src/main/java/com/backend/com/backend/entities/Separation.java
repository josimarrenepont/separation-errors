package com.backend.com.backend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    @Getter
    private Integer subTotPcMais;
    @Getter
    private Integer subTotPcMenos;
    @Getter
    private Integer subTotPcErrada;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "employee_separation", joinColumns = @JoinColumn(name = "separation_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private Set<Employee> employees = new HashSet<>();

    @Getter
    @ManyToMany(mappedBy = "separation", cascade = CascadeType.ALL)
    private Set<SeparationErrorHistory> errorHistory = new HashSet<>();

    public Separation() {
    }

    public Separation(Long id, Date date, String name, Integer codProduct, Integer pallet, Integer pcMais,
                      Integer pcMenos, Integer pcErrada, Integer errorPcMais, Integer errorPcMenos, Integer errorPcErrada, Integer subTotPcMais, Integer subTotPcMenos, Integer subTotPcErrada) {
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
        this.subTotPcMais = (subTotPcMais != null) ? subTotPcMais : 0;
        this.subTotPcMenos = (subTotPcMenos != null) ? subTotPcMenos: 0;
        this.subTotPcErrada = (subTotPcErrada != null) ? subTotPcErrada : 0;

    }

    public void updateAccumulatedSumOfErrors() {
        this.subTotPcMais += (this.pcMais != null ? this.pcMais : 0) + this.errorPcMais;
        this.subTotPcMenos += (this.pcMenos != null ? this.pcMenos : 0) + this.errorPcMenos;
        this.subTotPcErrada += (this.pcErrada != null ? this.pcErrada : 0) + this.errorPcErrada;
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


    public void setSubTotPcMais(Integer subTotPcMais) {
        if(this.subTotPcMais != null && this.subTotPcMais != 0){
            this.subTotPcMais += this.subTotPcMais;
        }
    }

    public void setSubTotPcMenos(Integer subTotPcMenos) {
        if(this.subTotPcMenos != null && this.subTotPcMenos != 0){
            this.subTotPcMenos += this.subTotPcMenos;
        }
    }

    public void setSubTotPcErrada(Integer subTotPcErrada) {
        if (this.subTotPcErrada != null && this.subTotPcErrada != 0) {
            this.subTotPcErrada += this.subTotPcErrada;
        }
    }

    public void addErrorToHistory(SeparationErrorHistory errorHistoryEntry) {
        this.errorHistory.add(errorHistoryEntry);

        // Atualize a soma acumulada de erros
        updateAccumulatedSumOfErrors();
    }

    public Integer getSubTotPcMais() {
        return (this.subTotPcMais != null ? this.subTotPcMais : 0) + (this.pcMais != null ? this.pcMais : 0) + (this.errorPcMais != null ? this.errorPcMais : 0);
    }

    public Integer getSubTotPcMenos() {
        return (this.subTotPcMenos != null ? this.subTotPcMenos : 0) + (this.pcMenos != null ? this.pcMenos : 0) + (this.errorPcMenos != null ? this.errorPcMenos : 0);
    }

    public Integer getSubTotPcErrada() {
        return (this.subTotPcErrada != null ? this.subTotPcErrada : 0) + (this.pcErrada != null ? this.pcErrada : 0) + (this.errorPcErrada != null ? this.errorPcErrada : 0);
    }

    public void addErrorTotPcMais(int selectedPcMais) {
        if (selectedPcMais != 0 && this.pcMais != 0 && this.errorPcMais != 0) {
            this.subTotPcMais += this.pcMais + selectedPcMais + this.errorPcMais;
            updateAccumulatedSumOfErrors();
        }
    }
    public void addErrorTotPcMenos(int selectedPcMenos) {
        if (selectedPcMenos != 0 && this.pcMenos != 0 && this.errorPcMenos != 0) {
            this.subTotPcMenos += this.pcMenos + selectedPcMenos + this.errorPcMenos;
            updateAccumulatedSumOfErrors();
        }
    }

    public void addErrorTotPcErrada(int selectedPcErrada) {
        if (selectedPcErrada != 0 && this.pcErrada != 0 && this.errorPcErrada != 0) {
            this.subTotPcErrada += this.pcErrada + selectedPcErrada + this.errorPcErrada;
            updateAccumulatedSumOfErrors();
        }
    }
    public void addEmployee(Employee existingEmployee) {
        employees.add(existingEmployee);

        // Atualize a soma acumulada de erros quando adiciona um funcionário
        updateAccumulatedSumOfErrors();
    }


    public void setErrorHistory(Set<SeparationErrorHistory> errorHistory) {

        this.errorHistory = errorHistory;
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

    public void addErrorHistory(SeparationErrorHistory separationErrorHistory) {
        if (this.errorHistory == null) {
            this.errorHistory = new HashSet<>();
        }
        this.errorHistory.add(separationErrorHistory);
    }

}