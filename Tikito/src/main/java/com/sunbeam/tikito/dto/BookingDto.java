package com.sunbeam.tikito.dto;

import java.util.List;
import com.sunbeam.tikito.enums.BookingStatus;
import com.sunbeam.tikito.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto 
{
	private Long bookingId;
	private Long userId;
	private Long showId;
	private List<Long> bookedSeatsIds;
	private double totalAmt;
	private PaymentStatus paymentStatus;
	private BookingStatus bookingStatus;
}
