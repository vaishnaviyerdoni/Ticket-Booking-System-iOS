package com.sunbeam.tikito.services;

import com.sunbeam.tikito.dto.CreateOrderRequestDto;
import com.sunbeam.tikito.dto.CreateOrderResponseDto;
import com.sunbeam.tikito.dto.VerifyPaymentDto;

public interface PaymentService {

    CreateOrderResponseDto createOrder(CreateOrderRequestDto dto);

    boolean verifyPayment(VerifyPaymentDto dto);

}