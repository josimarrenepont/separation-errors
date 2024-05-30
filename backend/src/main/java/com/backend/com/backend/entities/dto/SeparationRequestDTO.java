package com.backend.com.backend.entities.dto;

import com.backend.com.backend.entities.Separation;
import com.backend.com.backend.entities.SeparationErrorHistory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Setter
@Getter
public class SeparationRequestDTO{

    private Long id;
    private Date date;
    private String name;
    @Getter
    private Integer codProduct;
    @Getter
    private Integer pallet;
    private Integer errorPcMais;
    private Integer errorPcMenos;
    private Integer errorPcErrada;
    private Long employeeId;


    public SeparationRequestDTO() {
    }

    public SeparationRequestDTO(Separation entity) {
        BeanUtils.copyProperties(entity, this);
    }

    public void setDate(String string) {
        this.date = getDate();
    }


    public void setSeparation(Separation separation) {
        separation.setErrorHistory(separation.getErrorHistory());
    }
    public void setSeparationErrosHistory(SeparationErrorHistory errorHistory){
        errorHistory.setSeparation((Separation) errorHistory.getSeparation());
    }
}