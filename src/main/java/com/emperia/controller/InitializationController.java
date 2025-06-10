package com.emperia.controller;


import com.emperia.dto.*;
import com.emperia.exception.AppException;
import com.emperia.security.model.Role;
import com.emperia.security.model.RoleName;
import com.emperia.security.model.User;
import com.emperia.security.payload.SignUpRequest;
import com.emperia.security.repository.RoleRepository;
import com.emperia.security.repository.UserRepository;
import com.emperia.security.service.UserService;
import com.emperia.service.*;
import com.emperia.util.BillStatus;
import com.emperia.util.PriceTypes;
import com.emperia.util.ServiceTypes;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.Calendar;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/initialize")

public class InitializationController {
    @Autowired
    private BillService billService;

    @Autowired
    private BillStatusService billStatusService;

    @Autowired
    private ProviderService providerService;

    @Autowired
    private ProviderServiceService providerServiceService;

    @Autowired
    private HomeService homeService;

    @Autowired
    private ServiceTypeService serviceTypeService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @GetMapping(value="/bigBang")
    public ResponseEntity<String> initialize() throws IOException {
        initializeRoles();
        initializeUser();
        initializeBillStatuses();
        initializeServiceTypes();


        // Create providers

        ProviderDto eonProviderDto = new ProviderDto();
        eonProviderDto.setName("EON");

        ProviderDto electricaProviderDto = new ProviderDto();
        electricaProviderDto.setName("Electrica");

        ProviderDto delgazProviderDto = new ProviderDto();
        delgazProviderDto.setName("Delgaz");

        providerService.save(eonProviderDto);
        providerService.save(electricaProviderDto);
        providerService.save(delgazProviderDto);

        eonProviderDto = providerService.findByName(eonProviderDto.getName());
        electricaProviderDto = providerService.findByName(electricaProviderDto.getName());
        delgazProviderDto = providerService.findByName(delgazProviderDto.getName());


        // Services

        ServiceTypeDto serviceTypeGas = serviceTypeService.findByType(ServiceTypes.GAS);
        ServiceTypeDto serviceTypeElectricity = serviceTypeService.findByType(ServiceTypes.ELECTRICITY);
        ServiceTypeDto serviceTypeMaintainance = serviceTypeService.findByType(ServiceTypes.MAINTAINANCE);


        ProviderServicesDto eonGasServiceDto = new ProviderServicesDto();
        eonGasServiceDto.setProvider(eonProviderDto);
        eonGasServiceDto.setServiceType(serviceTypeGas);
        eonGasServiceDto.setPrice(100f);

        ProviderServicesDto electricaElectricityServiceDto = new ProviderServicesDto();
        electricaElectricityServiceDto.setProvider(electricaProviderDto);
        electricaElectricityServiceDto.setServiceType(serviceTypeElectricity);
        electricaElectricityServiceDto.setPrice(200f);

        ProviderServicesDto delgazMaintainanceServiceDto = new ProviderServicesDto();
        delgazMaintainanceServiceDto.setProvider(delgazProviderDto);
        delgazMaintainanceServiceDto.setServiceType(serviceTypeMaintainance);
        delgazMaintainanceServiceDto.setPrice(300f);

        providerServiceService.save(eonGasServiceDto);
        providerServiceService.save(electricaElectricityServiceDto);
        providerServiceService.save(delgazMaintainanceServiceDto);



        return new ResponseEntity<String>("Initialized user maria", HttpStatus.OK);
    }


    @GetMapping(value="/role")
    public ResponseEntity<String> initializeRoles()
    {
        roleRepository.save(new Role(RoleName.ROLE_USER));
        roleRepository.save(new Role(RoleName.ROLE_ADMIN));


        return new ResponseEntity<String>("Initialized roles", HttpStatus.OK);

    }

    @GetMapping(value="/user")
    public ResponseEntity<String> initializeUser()
    {

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setName("Maria Man");
        signUpRequest.setUsername("maria");
        signUpRequest.setPassword("maria");
        signUpRequest.setEmail("maria@gmail.com");

        userService.create(signUpRequest);

        return new ResponseEntity<String>("Initialized user maria", HttpStatus.OK);

    }

    @GetMapping(value="/billStatus")
    public ResponseEntity<String> initializeBillStatuses()
    {
        BillStatusDto pending = new BillStatusDto();

        pending.setStatus(BillStatus.PENDING);
        BillStatusDto expectingInput = new BillStatusDto();
        expectingInput.setStatus(BillStatus.EXPECTING_INPUT);
        BillStatusDto payed = new BillStatusDto();
        payed.setStatus(BillStatus.PAYED);
        BillStatusDto canceled = new BillStatusDto();
        canceled.setStatus(BillStatus.CANCELED);

        billStatusService.save(pending);
        billStatusService.save(expectingInput);
        billStatusService.save(payed);
        billStatusService.save(canceled);

        return new ResponseEntity<String>("Initialized statuses", HttpStatus.OK);

    }

    @GetMapping(value="/serviceType")
    public ResponseEntity<String> initializeServiceTypes()
    {
        ServiceTypeDto gas = new ServiceTypeDto();
        gas.setPriceType(PriceTypes.VARIABIL);
        gas.setType(ServiceTypes.GAS);

        ServiceTypeDto electricity = new ServiceTypeDto();
        electricity.setPriceType(PriceTypes.VARIABIL);
        electricity.setType(ServiceTypes.ELECTRICITY);

        ServiceTypeDto maintainance = new ServiceTypeDto();
        maintainance.setPriceType(PriceTypes.FIX);
        maintainance.setType(ServiceTypes.MAINTAINANCE);

        serviceTypeService.save(gas);
        serviceTypeService.save(electricity);
        serviceTypeService.save(maintainance);

        return new ResponseEntity<String>("Initialized service types", HttpStatus.OK);

    }



    @GetMapping(value="/dummyBill")
    public ResponseEntity<String> createDummyBill()
    //@CurrentUser UserPrincipal currentUser)
    {
        BillDto billDto = new BillDto();
        ProviderServicesDto myService = new ProviderServicesDto();
        myService.setId(1L);

        billDto.setProviderService(myService);
        billDto.setSum(120);
        billDto.setDeadline(Calendar.getInstance().getTime());
        billDto.setIssueDate(Calendar.getInstance().getTime());
        billDto.setHouseId(1L);
        System.out.println(billDto);



        billService.save(billDto);

        return new ResponseEntity<String>("Bill was created with great success!", HttpStatus.OK);

    }



    @GetMapping(value="/createDummyHouse")
    public ResponseEntity<String> createTestHouse() throws IOException
    //@CurrentUser UserPrincipal currentUser)
    {
        HomeDto testHome = new HomeDto();
        //testHome.setId(1L);
        testHome.setName("Vila");
        UserDto me = new UserDto();
        me.setUsername("Mar");
        testHome.setUserDto(me);
        testHome.setLocation(new LocationDto(0,0));

        homeService.save(testHome,1L);

        return new ResponseEntity<String>("Dummy house was created with great success!", HttpStatus.OK);

    }



    @GetMapping(value="/testBill")
    public ResponseEntity<String> createTestBill()
    //@CurrentUser UserPrincipal currentUser)
    {
        BillDto billDto = new BillDto();
        ProviderServicesDto myService = new ProviderServicesDto();
        myService.setId(1L);

        billDto.setProviderService(myService);
        billDto.setSum(120);
        billDto.setDeadline(Calendar.getInstance().getTime());
        billDto.setIssueDate(Calendar.getInstance().getTime());
        billDto.setHouseId(1L);
        System.out.println(billDto);



        billService.save(billDto);

        return new ResponseEntity<String>("Bill was created with great success!", HttpStatus.OK);

    }


}
