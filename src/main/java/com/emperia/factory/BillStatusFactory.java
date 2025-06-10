package com.emperia.factory;

import com.emperia.entity.BillStatusEntity;
import com.emperia.repository.BillStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BillStatusFactory {
    @Autowired
    private BillStatusRepository billStatusRepository;

    public BillStatusEntity get(String billStatus) {

        List<BillStatusEntity> statuses = billStatusRepository.findAll();

        return statuses
                .stream()
                .filter(status-> status.getStatus().equals(billStatus))
                .findFirst().get();
    }
}
