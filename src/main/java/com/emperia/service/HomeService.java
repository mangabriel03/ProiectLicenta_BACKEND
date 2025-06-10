package com.emperia.service;

import com.emperia.converter.HomeConverter;
import com.emperia.dto.HomeDto;
import com.emperia.dto.ProviderServicesDto;
import com.emperia.entity.HomeEntity;
import com.emperia.entity.LocationEntity;
import com.emperia.entity.ProviderServiceEntity;
import com.emperia.repository.*;
import com.emperia.security.model.User;
import com.emperia.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HomeService {

    @Autowired
    private HomeRepository homeRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HomeConverter homeConverter;

    @Autowired
    private ProviderServicesRepository providerServicesRepository;

    public List<HomeDto> getAllForUser(Long userId)
    {
        List<HomeEntity> homeEntities = homeRepository.findByUserId(userId);

        return homeConverter.toDtos(homeEntities);
    }

    public List<HomeDto> getAll()
    {

        List<HomeEntity> homeEntities = homeRepository.findAll();

        return homeConverter.toDtos(homeEntities);
    }


    public HomeDto get(Long id)
    {
        HomeEntity homeEntity = homeRepository.getById(id);

        return homeConverter.toDto(homeEntity);
    }

    public HomeDto delete(Long id)
    {

        HomeEntity homeEntity = homeRepository.getById(id);
        homeRepository.deleteById(id);

        HomeDto homeDto = homeConverter.toDto(homeEntity);

        return homeDto;
    }

    public List<HomeDto> search(String name)
    {
        List<HomeEntity> homeEntities = homeRepository.search(name);
        return homeConverter.toDtos(homeEntities);

    }

    public void save(HomeDto homeDto,

                     Long userId) throws IOException {


        HomeEntity homeEntity = new HomeEntity();
        homeEntity.setName(homeDto.getName());

        User user = userRepository.getById(userId);
        homeEntity.setUser(user);

        List<ProviderServiceEntity> services = new ArrayList<>();
        for(ProviderServicesDto service : homeDto.getServices()){
           ProviderServiceEntity serviceEntity = providerServicesRepository.getById(service.getId());
           services.add(serviceEntity);
        }
        homeEntity.setServices(services);

        LocationEntity locationEntity = new LocationEntity(homeDto.getLocation().getLatitude(), homeDto.getLocation().getLongitude());

        locationRepository.save(locationEntity);

        homeEntity.setLocation(locationEntity);

        homeRepository.save(homeEntity);

    }

    public void edit(HomeDto homeDto,
                     Long userId) throws IOException {

        HomeEntity homeEntity = homeRepository.getById(homeDto.getId());
        homeEntity.setName(homeDto.getName());

        User user = userRepository.getById(userId);
        homeEntity.setUser(user);

        List<ProviderServiceEntity> services = new ArrayList<>();
        for(ProviderServicesDto service : homeDto.getServices()){
            ProviderServiceEntity serviceEntity = providerServicesRepository.getById(service.getId());
            services.add(serviceEntity);
        }
        homeEntity.setServices(services);

        LocationEntity locationEntity = new LocationEntity(homeDto.getLocation().getLatitude(), homeDto.getLocation().getLongitude());

        locationRepository.save(locationEntity);

        homeEntity.setLocation(locationEntity);

        homeRepository.save(homeEntity);
    }
}
