package com.emperia.service;

import com.emperia.converter.ServiceTypeConverter;
import com.emperia.dto.ServiceTypeDto;
import com.emperia.entity.*;
import com.emperia.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceTypeService {

    @Autowired
    ServiceTypeConverter serviceTypeConverter;

    @Autowired
    ServiceTypeRepository serviceTypeRepository;


    public void save(ServiceTypeDto serviceTypeDto) {
        ServiceType serviceType = serviceTypeConverter.toEntity(serviceTypeDto);
        serviceTypeRepository.save(serviceType);
    }

    public ServiceTypeDto delete(Long id) {
        ServiceType serviceType = serviceTypeRepository.findById(id).get();
        serviceTypeRepository.delete(serviceType);
        ServiceTypeDto serviceTypeDto = serviceTypeConverter.toDto(serviceType);

        return serviceTypeDto;
    }

    public void update(ServiceTypeDto serviceTypeDto) {
        this.save(serviceTypeDto);
    }

    public ServiceTypeDto findByType(String type) {
        ServiceType serviceType = serviceTypeRepository.findByType(type);
        ServiceTypeDto serviceTypeDto = serviceTypeConverter.toDto(serviceType);

        return serviceTypeDto;
    }

    public List<ServiceTypeDto> findAll() {
        List<ServiceType> serviceTypeList = serviceTypeRepository.findAll();
        List<ServiceTypeDto> serviceTypeDtoList = serviceTypeList
                .stream()
                .map(serviceType -> serviceTypeConverter.toDto(serviceType))
                .collect(Collectors.toList());

        return serviceTypeDtoList;
    }

}
