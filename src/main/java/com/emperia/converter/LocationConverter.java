package com.emperia.converter;

import com.emperia.dto.LocationDto;
import com.emperia.entity.LocationEntity;
import org.springframework.stereotype.Component;

@Component
public class LocationConverter {
    public LocationDto toDto(LocationEntity locationEntity) {
        LocationDto locationDto = new LocationDto();
        locationDto.setLatitude(locationEntity.getLatitude());
        locationDto.setLongitude(locationEntity.getLongitude());

        return locationDto;
    }

    public LocationEntity toEntity(LocationDto locationDto) {
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setLatitude(locationDto.getLatitude());
        locationEntity.setLongitude(locationDto.getLongitude());

        return locationEntity;
    }
}
