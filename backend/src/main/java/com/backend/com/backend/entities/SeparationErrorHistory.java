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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "America/New_York")
    private Date date;

    @Getter
    @Column(name = "cod_product")
    private Integer codProduct;

    @Getter
    @Column(name = "pallet")
    private Integer pallet;

    @Getter
    @Column(name = "error_pc_mais")
    private Integer errorPcMais;

    @Getter
    @Column(name = "error_pc_menos")
    private Integer errorPcMenos;

    @Getter
    @Column(name = "error_pc_errada")
    private Integer errorPcErrada;

    @ManyToOne
    @JoinColumn(name = "separation_id")
    private Separation separation;


    public SeparationErrorHistory(){}

    public SeparationErrorHistory(Long id, String name, Date date, Integer codProduct,
                                  Integer pallet, Integer errorPcMais, Integer errorPcMenos, Integer errorPcErrada) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.codProduct = codProduct;
        this.pallet = pallet;
        this.errorPcMais = (errorPcMais != null) ? errorPcMais : 0;
        this.errorPcMenos = (errorPcMenos != null) ? errorPcMenos : 0;
        this.errorPcErrada = (errorPcErrada != null) ? errorPcErrada : 0;
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
