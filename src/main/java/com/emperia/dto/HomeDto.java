package com.emperia.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class HomeDto
{
    private Long id;
    private String name;
    //private String description;
    //private float price;
    private List<ProviderServicesDto> services;
    private List<BillDto> bills;
    private LocationDto location;
    private UserDto userDto;
    //private boolean isLiked;
}
