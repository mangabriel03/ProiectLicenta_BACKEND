package com.emperia.service;

import com.emperia.converter.HomeConverter;
import com.emperia.dto.HomeDto;
import com.emperia.dto.LocationDto;
import com.emperia.dto.ProviderServicesDto;
import com.emperia.entity.HomeEntity;
import com.emperia.entity.LocationEntity;
import com.emperia.entity.ProviderServiceEntity;
import com.emperia.repository.HomeRepository;
import com.emperia.repository.LocationRepository;
import com.emperia.repository.ProviderServicesRepository;
import com.emperia.security.model.User;
import com.emperia.security.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HomeServiceTest {

    @Mock
    private HomeRepository homeRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HomeConverter homeConverter;

    @Mock
    private ProviderServicesRepository providerServicesRepository;

    @InjectMocks
    private HomeService homeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllForUser() {
        Long userId = 1L;
        List<HomeEntity> homeEntities = new ArrayList<>();
        List<HomeDto> homeDtos = new ArrayList<>();

        when(homeRepository.findByUserId(userId)).thenReturn(homeEntities);
        when(homeConverter.toDtos(homeEntities)).thenReturn(homeDtos);

        List<HomeDto> result = homeService.getAllForUser(userId);

        assertEquals(homeDtos, result);
        verify(homeRepository).findByUserId(userId);
        verify(homeConverter).toDtos(homeEntities);
    }

    @Test
    public void testGetAll() {
        List<HomeEntity> homeEntities = new ArrayList<>();
        List<HomeDto> homeDtos = new ArrayList<>();

        when(homeRepository.findAll()).thenReturn(homeEntities);
        when(homeConverter.toDtos(homeEntities)).thenReturn(homeDtos);

        List<HomeDto> result = homeService.getAll();

        assertEquals(homeDtos, result);
        verify(homeRepository).findAll();
        verify(homeConverter).toDtos(homeEntities);
    }

    @Test
    public void testGet() {
        Long id = 1L;
        HomeEntity homeEntity = new HomeEntity();
        HomeDto homeDto = new HomeDto();

        when(homeRepository.getById(id)).thenReturn(homeEntity);
        when(homeConverter.toDto(homeEntity)).thenReturn(homeDto);

        HomeDto result = homeService.get(id);

        assertEquals(homeDto, result);
        verify(homeRepository).getById(id);
        verify(homeConverter).toDto(homeEntity);
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        HomeEntity homeEntity = new HomeEntity();
        homeEntity.setId(id);
        HomeDto homeDto = new HomeDto();
        homeDto.setId(id);

        when(homeRepository.getById(id)).thenReturn(homeEntity);
        doNothing().when(homeRepository).deleteById(id);
        when(homeConverter.toDto(homeEntity)).thenReturn(homeDto);

        HomeDto result = homeService.delete(id);

        assertEquals(homeDto, result);
        verify(homeRepository).getById(id);
        verify(homeRepository).deleteById(id);
        verify(homeConverter).toDto(homeEntity);
    }



    @Test
    public void testSearch() {
        String name = "Home";
        List<HomeEntity> homeEntities = new ArrayList<>();
        List<HomeDto> homeDtos = new ArrayList<>();

        when(homeRepository.search(name)).thenReturn(homeEntities);
        when(homeConverter.toDtos(homeEntities)).thenReturn(homeDtos);

        List<HomeDto> result = homeService.search(name);

        assertEquals(homeDtos, result);
        verify(homeRepository).search(name);
        verify(homeConverter).toDtos(homeEntities);
    }


    @Test
    public void testEdit() throws IOException {
        HomeDto homeDto = new HomeDto();
        Long userId = 1L;
        User user = new User();
        HomeEntity homeEntity = new HomeEntity();

        homeDto.setId(1L);
        homeDto.setName("Home");
        homeDto.setLocation(new LocationDto());
        homeDto.setServices(new ArrayList<>());

        when(homeRepository.getById(homeDto.getId())).thenReturn(homeEntity);
        when(userRepository.getById(userId)).thenReturn(user);
        when(providerServicesRepository.getById(any())).thenReturn(new ProviderServiceEntity());
        when(locationRepository.save(any())).thenReturn(new LocationEntity());
        when(homeRepository.save(homeEntity)).thenReturn(homeEntity);

        homeService.edit(homeDto, userId);

        verify(homeRepository).getById(homeDto.getId());
        verify(userRepository).getById(userId);
        verify(providerServicesRepository, times(homeDto.getServices().size())).getById(any());
        verify(locationRepository).save(any());
        verify(homeRepository).save(homeEntity);
    }

    // Add more test cases for other methods in HomeService

}