package com.emperia.service;

import com.emperia.converter.BillConverter;
import com.emperia.dto.BillDto;
import com.emperia.entity.BillEntity;
import com.emperia.entity.HomeEntity;
import com.emperia.repository.BillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BillServiceTest {

    @Mock
    private BillRepository billRepository;

    @Mock
    private BillConverter billConverter;

    @InjectMocks
    private BillService billService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSearch() {
        Long houseId = 1L;
        List<BillEntity> billEntities = new ArrayList<>();
        List<BillDto> billDtos = new ArrayList<>();

        when(billRepository.findByHomeId(houseId)).thenReturn(billEntities);
        when(billConverter.toDtos(billEntities)).thenReturn(billDtos);

        List<BillDto> result = billService.search(houseId);

        assertEquals(billDtos, result);
        verify(billRepository).findByHomeId(houseId);
        verify(billConverter).toDtos(billEntities);
    }

    @Test
    public void testGetAll() {
        List<BillEntity> billEntities = new ArrayList<>();
        List<BillDto> billDtos = new ArrayList<>();

        when(billRepository.findAll()).thenReturn(billEntities);
        when(billConverter.toDtos(billEntities)).thenReturn(billDtos);

        List<BillDto> result = billService.getAll();

        assertEquals(billDtos, result);
        verify(billRepository).findAll();
        verify(billConverter).toDtos(billEntities);
    }

    @Test
    public void testGet() {
        Long id = 1L;
        BillEntity billEntity = new BillEntity();
        BillDto billDto = new BillDto();

        when(billRepository.findFirstById(id)).thenReturn(billEntity);
        when(billConverter.toDto(billEntity)).thenReturn(billDto);

        BillDto result = billService.get(id);

        assertEquals(billDto, result);
        verify(billRepository).findFirstById(id);
        verify(billConverter).toDto(billEntity);
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        BillEntity billEntity = new BillEntity();
        billEntity.setId(id);

        when(billRepository.findFirstById(id)).thenReturn(billEntity);
        doNothing().when(billRepository).delete(billEntity);

        BillDto result = billService.delete(id);

        assertEquals(id, result.getId());
        verify(billRepository).findFirstById(id);
        verify(billRepository).delete(billEntity);
    }



    @Test
    public void testSave() {
        BillDto billDto = new BillDto();
        BillEntity billEntity = new BillEntity();

        when(billConverter.toEntity(billDto)).thenReturn(billEntity);
        when(billRepository.save(billEntity)).thenReturn(billEntity);

        billService.save(billDto);

        verify(billConverter).toEntity(billDto);
        verify(billRepository).save(billEntity);
    }

    @Test
    public void testEdit() {
        BillDto billDto = new BillDto();
        BillEntity billEntity = new BillEntity();

        when(billConverter.toEntity(billDto)).thenReturn(billEntity);
        when(billRepository.save(billEntity)).thenReturn(billEntity);

        billService.edit(billDto);

        verify(billConverter).toEntity(billDto);
        verify(billRepository).save(billEntity);
    }


    @Test
    public void testGetBillsGeneratedBetween() {
        Date startDate = new Date();
        Date endDate = new Date();
        List<BillEntity> billEntities = new ArrayList<>();
        List<BillDto> billDtos = new ArrayList<>();

        when(billRepository.findBillsGeneratedBetween(startDate, endDate)).thenReturn(billEntities);
        when(billConverter.toDtos(billEntities)).thenReturn(billDtos);

        List<BillDto> result = billService.getBillsGeneratedBetween(startDate, endDate);

        assertEquals(billDtos, result);
        verify(billRepository).findBillsGeneratedBetween(startDate, endDate);
        verify(billConverter).toDtos(billEntities);
    }

    // Add more test cases for other methods in BillService

}
