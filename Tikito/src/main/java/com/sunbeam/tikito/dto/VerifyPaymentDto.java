package com.sunbeam.tikito.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyPaymentDto {

    private String razorpayOrderId;

    private String razorpayPaymentId;

    private String razorpaySignature;

}