package com.sunbeam.tikito.dto;

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
public class CancelTicketDto
{
	private Long bookingId;
	private BookingStatus bookingStatus;
	private PaymentStatus paymentStatus;
}
