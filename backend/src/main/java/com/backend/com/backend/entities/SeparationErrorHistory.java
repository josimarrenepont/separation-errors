package com.backend.com.backend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Getter
@Entity
@Table(name = "tb_separation_error_history")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SeparationErrorHistory implements Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(name = "name")
    private String name;

    @Getter
    @Column(name = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Date date;

    @Getter
    @Column(name = "cod_product")
    private Integer codProduct;

    @Getter
    @Column(name = "pallet")
    private Integer pallet;

    @Getter
    @Column(name = "pc_mais")
    private Integer pcMais;

    @Getter
    @Column(name = "pc_menos")
    private Integer pcMenos;

    @Getter
    @Column(name = "pc_errada")
    private Integer pcErrada;

    @Getter
    @Column(name = "error_pc_mais")
    private Integer errorPcMais;

    @Getter
    @Column(name = "error_pc_menos")
    private Integer errorPcMenos;

    @Getter
    @Column(name = "error_pc_errada")
    private Integer errorPcErrada;
    @Column(name = "sub_tot_pc_mais")
    private Integer subTotPcMais;
    @Column(name = "sub_tot_pc_menos")
    private Integer subTotPcMenos;
    @Column(name = "sub_tot_pc_errada")
    private Integer subTotPcErrada;

    @ManyToOne
    @JoinColumn(name = "separation_id")
    private Separation separation;


    public SeparationErrorHistory(){}

    public SeparationErrorHistory(Long id, String name, Date date, Integer codProduct, Integer pallet, Integer pcMais, Integer pcMenos, Integer pcErrada, Integer errorPcMais, Integer errorPcMenos, Integer errorPcErrada, Integer subTotPcMais, Integer subTotPcMenos, Integer subTotPcErrada) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.codProduct = codProduct;
        this.pallet = pallet;
        this.pcMais = (pcMais != null) ? pcMais : 0;
        this.pcMenos = (pcMenos != null) ? pcMenos : 0;
        this.pcErrada = (pcErrada != null) ? pcErrada : 0;
        this.errorPcMais = (errorPcMais != null) ? errorPcMais : 0;
        this.errorPcMenos = (errorPcMenos != null) ? errorPcMenos : 0;
        this.errorPcErrada = (errorPcErrada != null) ? errorPcErrada : 0;
        this.subTotPcMais = (subTotPcMais != null) ? subTotPcMais : 0;
        this.subTotPcMenos = (subTotPcMenos != null) ? subTotPcMenos : 0;
        this.subTotPcErrada = (subTotPcErrada != null) ? subTotPcErrada : 0;
    }


    public void setSeparation(Separation separation) {
        this.separation = separation;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public SeparationErrorHistory(Integer codProduct) {
        this.setCodProduct(codProduct);
    }


    @PrePersist
    public void validateBeforePersist() {
        boolean allZeros = (this.pcMais == 0 && this.pcMenos == 0 && this.pcErrada == 0
                && this.errorPcMais == 0 && this.errorPcMenos == 0 && this.errorPcErrada == 0);

        if (allZeros) {
            throw new IllegalStateException("Todos os valores são zero. Não é possível salvar no banco de dados.");
        }

        this.subTotPcMais = add(this.subTotPcMais, this.pcMais, this.errorPcMais);
        this.subTotPcMenos = add(this.subTotPcMenos, this.pcMenos, this.errorPcMenos);
        this.subTotPcErrada = add(this.subTotPcErrada, this.pcErrada, this.errorPcErrada);
    }

    private Integer add(Integer... values) {
        int sum = 0;
        for (Integer value : values) {
            sum += (value != null) ? value : 0;
        }
        return sum;
    }



    private Integer add(Integer a, Integer b, Integer c) {
        return (a != null ? a : 0) + (b != null ? b : 0) + (c != null ? c : 0);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeparationErrorHistory that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
