package com.emperia.service;

import com.emperia.converter.BillStatusConverter;
import com.emperia.dto.BillStatusDto;
import com.emperia.entity.BillStatusEntity;
import com.emperia.repository.BillStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BillStatusServiceTest {

    @Mock
    private BillStatusRepository billStatusRepository;

    @Mock
    private BillStatusConverter billStatusConverter;

    @InjectMocks
    private BillStatusService billStatusService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        BillStatusDto billStatusDto = new BillStatusDto();
        BillStatusEntity billStatusEntity = new BillStatusEntity();

        when(billStatusConverter.toEntity(billStatusDto)).thenReturn(billStatusEntity);
        when(billStatusRepository.save(billStatusEntity)).thenReturn(billStatusEntity);

        billStatusService.save(billStatusDto);

        verify(billStatusConverter).toEntity(billStatusDto);
        verify(billStatusRepository).save(billStatusEntity);
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        BillStatusEntity billStatusEntity = new BillStatusEntity();
        billStatusEntity.setId(id);
        BillStatusDto billStatusDto = new BillStatusDto();
        billStatusDto.setId(id);

        when(billStatusRepository.findById(id)).thenReturn(Optional.of(billStatusEntity));
        doNothing().when(billStatusRepository).delete(billStatusEntity);
        when(billStatusConverter.toDto(billStatusEntity)).thenReturn(billStatusDto);

        BillStatusDto result = billStatusService.delete(id);

        assertEquals(billStatusDto, result);
        verify(billStatusRepository).findById(id);
        verify(billStatusRepository).delete(billStatusEntity);
        verify(billStatusConverter).toDto(billStatusEntity);
    }

}