package com.emperia.converter;

import com.emperia.dto.BillStatusDto;
import com.emperia.dto.ServiceTypeDto;
import com.emperia.entity.BillEntity;
import com.emperia.entity.BillStatusEntity;
import com.emperia.entity.ServiceType;
import org.springframework.stereotype.Component;

@Component
public class BillStatusConverter {
    public BillStatusDto toDto(BillStatusEntity billStatusEntity) {

        BillStatusDto billStatusDto = new BillStatusDto();

        billStatusDto.setId(billStatusEntity.getId());
        billStatusDto.setStatus(billStatusEntity.getStatus());

        return billStatusDto;
    }

    public BillStatusEntity toEntity(BillStatusDto billStatusDto) {
        BillStatusEntity billStatusEntity = new BillStatusEntity();
        billStatusEntity.setId(billStatusDto.getId());
        billStatusEntity.setStatus(billStatusDto.getStatus());

        return billStatusEntity;
    }
}
