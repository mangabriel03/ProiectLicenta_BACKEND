package com.emperia.service;

import com.emperia.converter.BillConverter;
import com.emperia.dto.BillDto;
import com.emperia.dto.BillIndexDto;
import com.emperia.entity.BillEntity;
import com.emperia.entity.BillStatusEntity;
import com.emperia.factory.BillStatusFactory;
import com.emperia.repository.*;
import com.emperia.util.BillStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PaymentService {

    @Autowired
    private BillRepository billRepository;
    @Autowired
    private BillConverter billConverter;

    @Autowired
    private BillStatusFactory billStatusFactory;


    public BillDto submitIndex(BillIndexDto billIndexDto) {
        BillEntity billEntity = billRepository.findFirstById(billIndexDto.getBillId());

        // update sum
        float newSum = billEntity.getProviderService().getPrice() * billIndexDto.getIndex();
        billEntity.setSum(newSum);

        // update status
        BillStatusEntity pendingStatus = billStatusFactory.get(BillStatus.PENDING);
        billEntity.setStatus(pendingStatus);

        // update bill
        billRepository.save(billEntity);

        return billConverter.toDto(billEntity);
    }
}
