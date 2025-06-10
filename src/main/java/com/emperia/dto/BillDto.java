package com.emperia.dto;


import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BillDto {
    private Long id;
    private float sum;
    private Long houseId;
    private ProviderServicesDto providerService;
    private Date issueDate;
    private Date deadline;
    private BillStatusDto status;


}
