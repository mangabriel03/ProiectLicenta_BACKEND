package com.emperia.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProviderServicesDto {
    private long id;
    //tipul de serviciu
    private ServiceTypeDto serviceType;
    private float price;
    private ProviderDto provider;
}
