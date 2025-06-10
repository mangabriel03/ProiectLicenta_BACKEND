package com.emperia.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ConfirmPaymentDto {

    @NotNull
    private List<Long> billIds;

}