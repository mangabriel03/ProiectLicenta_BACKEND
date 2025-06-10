package com.emperia.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BillStatusDto {
    private Long id;
    private String status;
}
