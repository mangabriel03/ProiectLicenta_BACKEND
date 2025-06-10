package com.emperia.service;
import com.emperia.converter.PorviderConverter;
import com.emperia.dto.*;
import com.emperia.entity.*;
import com.emperia.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderService {
    @Autowired
    ProviderRepository providerRepository;

    @Autowired
    PorviderConverter providerConverter;

    public void save(ProviderDto providerDto) {
        ProviderEntity providerEntity = providerConverter.toEntity(providerDto);
        providerRepository.save(providerEntity);

    }

    public void update(ProviderDto providerDto) {
        ProviderEntity providerEntity = providerConverter.toEntity(providerDto);
        providerRepository.save(providerEntity);

    }

    public void delete(Long id) {
        providerRepository.deleteById(id);

    }

    public ProviderDto findByName(String providerName) {
        ProviderEntity providerEntity = providerRepository.findByName(providerName);

        ProviderDto providerDto = new ProviderDto();
        providerDto.setName(providerEntity.getName());
        providerDto.setId(providerEntity.getId());

        return providerDto;
    }

    public List<ProviderDto> findAll() {

        List<ProviderEntity> providerEntityList = providerRepository.findAll();
        return providerConverter.toDtos(providerEntityList);
    }

}
