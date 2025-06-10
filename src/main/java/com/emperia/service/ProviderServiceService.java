package com.emperia.service;

import com.emperia.converter.ProviderServiceConverter;
import com.emperia.dto.ProviderServicesDto;
import com.emperia.entity.ProviderServiceEntity;
import com.emperia.repository.ProviderRepository;
import com.emperia.repository.ProviderServicesRepository;
import com.emperia.repository.ServiceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderServiceService {
    @Autowired
    ProviderRepository providerRepository;
    @Autowired
    ProviderServicesRepository providerServicesRepository;
    @Autowired
    ProviderServiceConverter providerServiceConverter;
    @Autowired
    ServiceTypeRepository serviceTypeRepository;

    public void save(ProviderServicesDto providerServiceDto) {

        ProviderServiceEntity providerServiceEntity = providerServiceConverter.toEntity(providerServiceDto);

        providerServicesRepository.save(providerServiceEntity);
    }

    public ProviderServicesDto delete(Long id) {

        ProviderServiceEntity providerServiceEntity = providerServicesRepository.getById(id);
        providerServicesRepository.deleteById(id);

        return providerServiceConverter.toDto(providerServiceEntity);
    }


    public List<ProviderServicesDto> findAll() {
        List<ProviderServiceEntity> servicesEntities = providerServicesRepository.findAll();

        //System.out.println("SERVICE getAllServices: "+servicesEntities.toString());
        return providerServiceConverter.toDtos(servicesEntities);
    }

    public void update(ProviderServicesDto providerServiceDto) {

        ProviderServiceEntity providerServiceEntity = providerServiceConverter.toEntity(providerServiceDto);

        providerServicesRepository.save(providerServiceEntity);
    }


}
