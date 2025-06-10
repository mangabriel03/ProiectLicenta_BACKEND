package com.emperia.converter;

import com.emperia.dto.*;
import com.emperia.entity.BillEntity;
import com.emperia.entity.HomeEntity;
import com.emperia.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HomeConverter {
    @Autowired
    private BillRepository billRepository;

    @Autowired
    private ProviderServiceConverter providerServiceConvertor;

    @Autowired
    private LocationConverter locationConverter;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private BillConverter billConverter;

    public HomeDto toDto(HomeEntity homeEntity) {
        HomeDto homeDto = new HomeDto();

        homeDto.setId(homeEntity.getId());
        homeDto.setName(homeEntity.getName());

        homeDto.setLocation(locationConverter.toDto(homeEntity.getLocation()));
        homeDto.setUserDto(userConverter.toDto(homeEntity.getUser()));

        List<ProviderServicesDto> providerServicesDtoList = providerServiceConvertor.toDtos(homeEntity.getServices());
        homeDto.setServices(providerServicesDtoList);

        List<BillEntity> billEntities = billRepository.findByHomeId(homeEntity.getId());
        List<BillDto> bills =  billConverter.toDtos(billEntities);

        homeDto.setBills(bills);

        return homeDto;

    }

    public List<HomeDto> toDtos(List<HomeEntity> homeEntities) {
        if(homeEntities == null) {
            return new ArrayList<>();
        }

        return homeEntities.stream()
                .map(currentHome -> toDto(currentHome)
                )
                .collect(Collectors.toList());
    }

}
