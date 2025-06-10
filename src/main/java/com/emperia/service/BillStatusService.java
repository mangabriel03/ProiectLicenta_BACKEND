package com.emperia.service;

import com.emperia.converter.BillStatusConverter;
import com.emperia.dto.BillStatusDto;
import com.emperia.entity.*;
import com.emperia.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillStatusService {

    @Autowired
    private BillStatusConverter billStatusConverter;
    @Autowired
    private BillStatusRepository billStatusRepository;

    
    public void save(BillStatusDto billStatusDto) {
        BillStatusEntity statusEntity = billStatusConverter.toEntity(billStatusDto);
        billStatusRepository.save(statusEntity);
    }

    public BillStatusDto delete(Long id) {
        BillStatusEntity serviceType = billStatusRepository.findById(id).get();
        billStatusRepository.delete(serviceType);
        BillStatusDto billStatusDto = billStatusConverter.toDto(serviceType);

        return billStatusDto;
    }

    public void update(BillStatusDto billStatusDto) {
        this.save(billStatusDto);
    }


    public List<BillStatusDto> findAll() {
        List<BillStatusEntity> serviceTypeList = billStatusRepository.findAll();
        List<BillStatusDto> billStatusDtoList = serviceTypeList
                .stream()
                .map(serviceType -> billStatusConverter.toDto(serviceType))
                .collect(Collectors.toList());

        return billStatusDtoList;
    }


}
