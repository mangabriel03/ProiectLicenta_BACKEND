package com.emperia.converter;

import com.emperia.dto.BillDto;
import com.emperia.dto.BillStatusDto;
import com.emperia.dto.HomeDto;
import com.emperia.dto.ProviderServicesDto;
import com.emperia.entity.BillEntity;
import com.emperia.entity.BillStatusEntity;
import com.emperia.entity.HomeEntity;
import com.emperia.entity.ProviderServiceEntity;
import com.emperia.repository.BillStatusRepository;
import com.emperia.repository.HomeRepository;
import com.emperia.repository.ProviderServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BillConverter {
    @Autowired
    private HomeConverter homeConverter;
    @Autowired
    private ProviderServiceConverter providerServiceConvertor;
    @Autowired
    private BillStatusConverter billStatusConverter;

    @Autowired
    private ProviderServicesRepository providerServicesRepository;
    @Autowired
    private HomeRepository homeRepository;
    @Autowired
    private BillStatusRepository billStatusRepository;


    public BillDto toDto(BillEntity billEntity) {
        BillDto billDto = new BillDto();

        billDto.setId(billEntity.getId());
        billDto.setSum(billEntity.getSum());
        billDto.setIssueDate(billEntity.getIssueDate());

        ProviderServicesDto psDto = providerServiceConvertor.toDto(billEntity.getProviderService());
        billDto.setProviderService(psDto);

        billDto.setDeadline(billEntity.getDeadline());
        billDto.setHouseId(billEntity.getHome().getId());

        BillStatusDto billStatusDto = billStatusConverter.toDto(billEntity.getStatus());
        billDto.setStatus(billStatusDto);

        return billDto;
    }

    public BillEntity toEntity(BillDto billDto) {
        BillEntity billEntity = new BillEntity();
        billEntity.setId(billDto.getId());
        billEntity.setIssueDate(billDto.getIssueDate());
        billEntity.setDeadline(billDto.getDeadline());
        billEntity.setSum(billDto.getSum());

        ProviderServiceEntity providerServiceEntity = providerServicesRepository.getById(billDto.getProviderService().getId());
        billEntity.setProviderService(providerServiceEntity);

        HomeEntity homeEntity = homeRepository.getById(billDto.getHouseId());
        billEntity.setHome(homeEntity);

        BillStatusEntity billStatusEntity = billStatusRepository.getById(billDto.getStatus().getId());
        billEntity.setStatus(billStatusEntity);

        return billEntity;

    }

    public List<BillDto> toDtos(List<BillEntity> billEntities) {
        if(billEntities == null) {
            return new ArrayList<>();
        }

        return billEntities.stream()
                .map(currentBill -> toDto(currentBill)
                )
                .collect(Collectors.toList());
    }

}
