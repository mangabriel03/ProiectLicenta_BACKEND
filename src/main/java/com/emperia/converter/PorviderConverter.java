package com.emperia.converter;

import com.emperia.dto.ProviderDto;
import com.emperia.entity.ProviderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PorviderConverter {

    @Autowired
    private ProviderServiceConverter providerServiceConverter;

    public ProviderDto toDto(ProviderEntity provider) {
        ProviderDto providerDto = new ProviderDto();
        providerDto.setId(provider.getId());
        providerDto.setName(provider.getName());

        return providerDto;
    }

    public ProviderEntity toEntity(ProviderDto providerDto) {
        ProviderEntity providerEntity = new ProviderEntity();

        providerEntity.setId(providerDto.getId());
        providerEntity.setName(providerDto.getName());
        providerEntity.setServices(providerServiceConverter.toEntities(providerDto.getServices()));

        return providerEntity;
    }

    public List<ProviderDto> toDtos(List<ProviderEntity> providerEntities) {
        if(providerEntities == null) {
            return new ArrayList<>();
        }

        return providerEntities.stream()
                .map(providerEntity -> toDto(providerEntity))
                .collect(Collectors.toList());
    }
}
