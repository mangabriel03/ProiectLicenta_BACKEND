package com.emperia.converter;

import com.emperia.dto.ServiceTypeDto;
import com.emperia.entity.ServiceType;
import org.springframework.stereotype.Component;

@Component
public class ServiceTypeConverter {
    public ServiceTypeDto toDto(ServiceType serviceType) {
        ServiceTypeDto serviceTypeDto = new ServiceTypeDto();
        serviceTypeDto.setId(serviceType.getId());
        serviceTypeDto.setType(serviceType.getType());
        serviceTypeDto.setPriceType(serviceType.getPriceType());

        return serviceTypeDto;
    }

    public ServiceType toEntity(ServiceTypeDto serviceTypeDto) {
        ServiceType serviceType = new ServiceType();
        serviceType.setType(serviceTypeDto.getType());
        serviceType.setPriceType(serviceTypeDto.getPriceType());
        serviceType.setId(serviceTypeDto.getId());

        return serviceType;
    }
}
