package com.emperia.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreatePaymentResponseDto {

    @NotNull
    private String clientSecret;

}