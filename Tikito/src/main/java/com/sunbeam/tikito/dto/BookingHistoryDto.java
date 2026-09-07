package com.sunbeam.tikito.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.sunbeam.tikito.enums.BookingStatus;
import com.sunbeam.tikito.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingHistoryDto {

    private Long bookingId;

    private Long showId;

    private String eventName;

    private String venueName;

    private LocalDate showDate;

    private LocalTime showStartTime;

    private List<String> seatNumbers;

    private Double totalAmt;

    private PaymentStatus paymentStatus;

    private BookingStatus bookingStatus;

    private LocalDateTime bookingDate;
}
