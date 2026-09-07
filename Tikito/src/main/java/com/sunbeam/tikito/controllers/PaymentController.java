package com.sunbeam.tikito.controllers;

import org.springframework.web.bind.annotation.*;

import com.sunbeam.tikito.dto.CreateOrderRequestDto;
import com.sunbeam.tikito.dto.VerifyPaymentDto;
import com.sunbeam.tikito.services.PaymentService;
import com.sunbeam.tikito.utils.Resp;

@RestController
@RequestMapping("/tikito/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create-order")
    public Resp<?> createOrder(
            @RequestBody CreateOrderRequestDto dto) {

        return Resp.success(
                paymentService.createOrder(dto));
    }

    @PostMapping("/verify")
    public Resp<?> verifyPayment(
            @RequestBody VerifyPaymentDto dto) {

        boolean verified =
                paymentService.verifyPayment(dto);

        if (verified) {
            return Resp.success("Payment Verified");
        }

        return Resp.error("Invalid Payment Signature");
    }

}