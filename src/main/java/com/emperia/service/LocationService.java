package com.emperia.service;

import com.emperia.dto.HomeLocationDto;
import com.emperia.dto.LocationDto;
import com.emperia.entity.HomeEntity;
import com.emperia.entity.LocationEntity;
import com.emperia.repository.HomeRepository;
import com.emperia.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private HomeRepository homeRepository;

    public List<LocationDto> getAll()
    {


        List<LocationEntity> locationEntities = locationRepository.findAll();

        List<LocationDto> locationDtos = locationEntities.stream()
                    .map(locationEntity -> new LocationDto(locationEntity.getLatitude(), locationEntity.getLongitude()))
                    .collect(Collectors.toList());

        return locationDtos;
    }

    public List<HomeLocationDto> getAllHomeLocations()
    {


        List<HomeEntity> homeEntities = homeRepository.findAll();

        List<HomeLocationDto> homeLocationDtos = new ArrayList<>();

        homeEntities.forEach(homeEntity -> {
            if(homeEntity.getLocation() != null) {
                HomeLocationDto home= new HomeLocationDto(homeEntity.getId(),
                                    homeEntity.getLocation().getLatitude(),
                                    homeEntity.getLocation().getLongitude());
                homeLocationDtos.add(home);
            }
        });


        return homeLocationDtos;
    }

}
