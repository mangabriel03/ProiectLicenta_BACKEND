package com.emperia.converter;

import com.emperia.dto.ProviderDto;
import com.emperia.dto.ProviderServicesDto;
import com.emperia.dto.ServiceTypeDto;
import com.emperia.entity.ProviderEntity;
import com.emperia.entity.ProviderServiceEntity;
import com.emperia.entity.ServiceType;
import com.emperia.repository.ProviderRepository;
import com.emperia.repository.ServiceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProviderServiceConverter {

    @Autowired
    private PorviderConverter providerConverter;

    @Autowired
    ServiceTypeRepository serviceTypeRepository;

    @Autowired
    ProviderRepository providerRepository;


    public ProviderServicesDto toDto(ProviderServiceEntity providerServiceEntity) {
        ProviderServicesDto psDto = new ProviderServicesDto();
        psDto.setId(providerServiceEntity.getId());
        ProviderDto providerDto = providerConverter.toDto(providerServiceEntity.getProvider());//new ProviderDto(providerServiceEntity.getProvider().getId(),providerServiceEntity.getProvider().getName());//
        psDto.setProvider(providerDto);
        psDto.setPrice(providerServiceEntity.getPrice());
        ServiceTypeDto stDto = new ServiceTypeDto(providerServiceEntity.getServiceType().getId(),providerServiceEntity.getServiceType().getType(),providerServiceEntity.getServiceType().getPriceType());//serviceTypeConverter.serviceTypeEntityToDto(providerServiceEntity.getServiceType());
        psDto.setServiceType(stDto);
        return psDto;
    }

    public ProviderServiceEntity toEntity(ProviderServicesDto providerServicesDto) {
        ProviderServiceEntity providerServiceEntity = new ProviderServiceEntity();

        providerServiceEntity.setId(providerServicesDto.getId());

        ServiceType serviceTypeEntity = serviceTypeRepository.getById(providerServicesDto.getServiceType().getId());
        providerServiceEntity.setServiceType(serviceTypeEntity);

        providerServiceEntity.setPrice(providerServicesDto.getPrice());

        ProviderEntity providerEntity = providerRepository.getById(providerServicesDto.getProvider().getId());
        providerServiceEntity.setProvider(providerEntity);

        return providerServiceEntity;
    }

    public List<ProviderServicesDto> toDtos(List<ProviderServiceEntity> services) {
        if(services == null) {
            return new ArrayList<>();
        }

        return services.stream()
                .map(currentService -> toDto(currentService)
                )
                .collect(Collectors.toList());
    }

    public List<ProviderServiceEntity> toEntities(List<ProviderServicesDto> services) {
        if(services == null) {
            return new ArrayList<>();
        }
        return services.stream()
                .map(currentService -> toEntity(currentService)
                )
                .collect(Collectors.toList());
    }
}
