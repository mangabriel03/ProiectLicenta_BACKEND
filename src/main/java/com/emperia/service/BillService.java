package com.emperia.service;

import com.emperia.converter.BillConverter;
import com.emperia.dto.*;
import com.emperia.entity.*;
import com.emperia.factory.BillStatusFactory;
import com.emperia.repository.*;
import com.emperia.util.BillStatus;
import com.emperia.util.PriceTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private HomeRepository homeRepository;

    @Autowired
    private BillConverter billConverter;

    @Autowired
    private BillStatusFactory billStatusFactory;


    public List<BillDto> search(Long houseId) {
        List<BillEntity> billEntities = billRepository.findByHomeId(houseId);
        return billConverter.toDtos(billEntities);
    }

    public List<BillDto> getAll() {
        List<BillEntity> billEntities = billRepository.findAll();

        return billConverter.toDtos(billEntities);
    }

    public BillDto get(Long id) {
        BillEntity billEntity = billRepository.findFirstById(id);

        return billConverter.toDto(billEntity);
    }

    public BillDto delete(Long idNumeric) {

        BillEntity billEntity = billRepository.findFirstById(idNumeric);
        billRepository.delete(billEntity);
        BillDto billDto = new BillDto();
        billDto.setId(billEntity.getId());

        return billDto;
    }

    public void save(BillDto billDto) {

        BillEntity billEntity = billConverter.toEntity(billDto);


        billRepository.save(billEntity);
    }


    public void edit(BillDto billDto){
        BillEntity billEntity = billConverter.toEntity(billDto);

        billRepository.save(billEntity);
    }

    public List<BillDto> getAllForUser(Long id) {
        List<BillEntity> bills = homeRepository.findByUserId(id)
                .stream()
                .map(home -> home.getId())
                .flatMap(homeId -> billRepository.findByHomeId(homeId).stream())
                .collect(Collectors.toList());

        List<BillDto> billsOfAUser = billConverter.toDtos(bills);
        return billsOfAUser;
    }

    public List<BillDto> getBillsGeneratedBetween(Date startDate, Date endDate) {
        if (endDate == null) {
            endDate = new Date(); // Set the end date to today if not provided
        }
        return billConverter.toDtos(billRepository.findBillsGeneratedBetween(startDate, endDate));
    }


    private BillEntity generateBillForService(ProviderServiceEntity providerServiceEntity) {

        BillEntity newBill = new BillEntity();

        newBill.setProviderService(providerServiceEntity);

        BillStatusEntity actualStatus;
        switch(providerServiceEntity.getServiceType().getPriceType()){
            case PriceTypes.FIX:
                actualStatus =  billStatusFactory.get(BillStatus.PENDING);;
                break;
            case PriceTypes.VARIABIL:
                actualStatus =  billStatusFactory.get(BillStatus.EXPECTING_INPUT);
                break;
            default:
                actualStatus = null;
        }
        newBill.setStatus(actualStatus);

        float actualPrice=0;
        if(PriceTypes.FIX.equals(providerServiceEntity.getServiceType().getPriceType())){
            actualPrice = providerServiceEntity.getPrice();
        }
        newBill.setSum(actualPrice);

        newBill.setIssueDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        newBill.setDeadline(Date.from(LocalDate.now().plusMonths(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        return newBill;
    }

    private List<BillEntity> generateBillsForHome(HomeEntity homeEntity) {
        List<BillEntity> homeBills = new ArrayList<>();

        homeEntity.getServices().forEach(service-> {
            BillEntity serviceBill = generateBillForService(service);
            serviceBill.setHome(homeEntity);

            homeBills.add(serviceBill);
        });

        return homeBills;
    }

    public void generateBillsForHome(Long homeId) {
        HomeEntity homeEntity = homeRepository.getById(homeId);
        List<BillEntity> homeBills = generateBillsForHome(homeEntity);
        billRepository.saveAll(homeBills);
    }

    public void generateAllBills(){

        List<BillEntity> bills = new ArrayList<>();

        List<HomeEntity> homes = homeRepository.findAll();

        for(HomeEntity home : homes){
            List<BillEntity> homeBills = generateBillsForHome(home);
            bills.addAll(homeBills);
        }

        billRepository.saveAll(bills);

    }

    public Long computeAmountForBills(List<Long> billIds) {
        Long totalAmount = billIds.stream()
                .map(billId -> billRepository.findById(billId).map(BillEntity::getSum).orElse(0f))
                .map(Float::longValue) // Convert float to Long
                .reduce(0L, Long::sum);

        return totalAmount;
    }


    public void pay(Long billId) {
        BillEntity billEntity = billRepository.findFirstById(billId);
        billEntity.setStatus(billStatusFactory.get(BillStatus.PAYED));

        billRepository.save(billEntity);
    }

}
