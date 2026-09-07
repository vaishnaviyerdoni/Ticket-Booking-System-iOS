package com.sunbeam.tikito.dto;

import java.util.List;

import com.sunbeam.tikito.entity.BookedSeatsEntity;
import com.sunbeam.tikito.entity.ShowEntity;
import com.sunbeam.tikito.entity.UserEntity;
import com.sunbeam.tikito.enums.BookingStatus;
import com.sunbeam.tikito.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AllBookingsDto 
{
	private Long bookingId;
	private long userId;
	private long showId;
	private List<BookedSeatsEntity> bookedSeats;
	private double totalAmt;
	private PaymentStatus paymentStatus;
	private BookingStatus bookingStatus;
}