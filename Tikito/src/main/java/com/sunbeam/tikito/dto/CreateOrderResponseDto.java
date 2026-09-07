package com.sunbeam.tikito.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderResponseDto {

    private String orderId;

    private Integer amount;

    private String currency;

    private String keyId;

}