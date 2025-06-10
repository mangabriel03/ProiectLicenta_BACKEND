package com.emperia.service;

import com.emperia.converter.BillConverter;
import com.emperia.dto.BillDto;
import com.emperia.dto.BillIndexDto;
import com.emperia.entity.BillEntity;
import com.emperia.entity.BillStatusEntity;
import com.emperia.entity.ProviderServiceEntity;
import com.emperia.factory.BillStatusFactory;
import com.emperia.repository.BillRepository;
import com.emperia.util.BillStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {

    @Mock
    private BillRepository billRepository;

    @Mock
    private BillConverter billConverter;

    @Mock
    private BillStatusFactory billStatusFactory;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSubmitIndex() {
        Long billId = 1L;
        float index = 1.5f;
        float price = 10.0f;

        BillIndexDto billIndexDto = new BillIndexDto();
        billIndexDto.setBillId(billId);
        billIndexDto.setIndex(index);

        ProviderServiceEntity providerServiceEntity = new ProviderServiceEntity();
        providerServiceEntity.setPrice(price);

        BillEntity billEntity = new BillEntity();
        billEntity.setId(billId);
        billEntity.setSum(price * index);
        billEntity.setProviderService(providerServiceEntity);

        BillStatusEntity pendingStatus = new BillStatusEntity();
        pendingStatus.setId(1L);
        pendingStatus.setStatus(BillStatus.PENDING);
        billEntity.setStatus(pendingStatus);

        BillDto expectedDto = new BillDto();
        expectedDto.setId(billId);

        when(billRepository.findFirstById(billId)).thenReturn(billEntity);
        when(billStatusFactory.get(BillStatus.PENDING)).thenReturn(pendingStatus);
        when(billConverter.toDto(billEntity)).thenReturn(expectedDto);
        when(billRepository.save(billEntity)).thenReturn(billEntity);

        BillDto result = paymentService.submitIndex(billIndexDto);

        assertEquals(expectedDto, result);
        assertEquals(price * index, billEntity.getSum());
        assertEquals(pendingStatus, billEntity.getStatus());
        verify(billRepository).findFirstById(billId);
        verify(billStatusFactory).get(BillStatus.PENDING);
        verify(billConverter).toDto(billEntity);
        verify(billRepository).save(billEntity);
    }

}