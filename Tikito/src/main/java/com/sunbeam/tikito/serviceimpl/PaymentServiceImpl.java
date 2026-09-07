package com.sunbeam.tikito.serviceimpl;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.sunbeam.tikito.dto.CreateOrderRequestDto;
import com.sunbeam.tikito.dto.CreateOrderResponseDto;
import com.sunbeam.tikito.dto.VerifyPaymentDto;
import com.sunbeam.tikito.services.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    @Override
    public CreateOrderResponseDto createOrder(CreateOrderRequestDto dto) {

        try {

            if (dto.getAmount() == null || dto.getAmount() < 1) {
                throw new RuntimeException("Amount should be greater than 0");
            }

            RazorpayClient client =
                    new RazorpayClient(keyId, keySecret);

            JSONObject options = new JSONObject();

            // Razorpay expects amount in paise
            options.put("amount", (int) (dto.getAmount() * 100));

            options.put("currency",
                    dto.getCurrency() == null
                            ? "INR"
                            : dto.getCurrency());

            options.put("receipt",
                    dto.getReceipt());

            Order order = client.orders.create(options);

            CreateOrderResponseDto response =
                    new CreateOrderResponseDto();

            response.setOrderId(
                    order.get("id"));

            response.setAmount(
                    order.get("amount"));

            response.setCurrency(
                    order.get("currency"));

            response.setKeyId(
                    keyId);

            return response;

        }

        catch (Exception e) {

            throw new RuntimeException(
                    "Unable to create Razorpay Order : "
                            + e.getMessage());

        }

    }

    @Override
    public boolean verifyPayment(VerifyPaymentDto dto) {

        try {

            String data =
                    dto.getRazorpayOrderId()
                            + "|"
                            + dto.getRazorpayPaymentId();

            String generatedSignature =
                    hmacSHA256(data, keySecret);

            return generatedSignature.equals(
                    dto.getRazorpaySignature());

        }

        catch (Exception e) {

            throw new RuntimeException(
                    "Payment Verification Failed");

        }

    }

    private String hmacSHA256(
            String data,
            String secret)
            throws Exception {

        Mac sha256Hmac =
                Mac.getInstance("HmacSHA256");

        SecretKeySpec secretKey =
                new SecretKeySpec(
                        secret.getBytes(),
                        "HmacSHA256");

        sha256Hmac.init(secretKey);

        byte[] hash =
                sha256Hmac.doFinal(
                        data.getBytes());

        StringBuilder result =
                new StringBuilder();

        for (byte b : hash) {

            result.append(
                    String.format("%02x", b));

        }

        return result.toString();

    }

}