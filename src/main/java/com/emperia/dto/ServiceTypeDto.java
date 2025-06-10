package com.emperia.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ServiceTypeDto {
    Long id;
    String type;
    String priceType;
}
