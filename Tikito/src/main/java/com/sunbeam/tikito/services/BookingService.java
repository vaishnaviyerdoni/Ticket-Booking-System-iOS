package com.sunbeam.tikito.services;

import java.util.List;

import com.sunbeam.tikito.dto.AllBookingsDto;
import com.sunbeam.tikito.dto.AvailableSeatsDto;
import com.sunbeam.tikito.dto.BookingHistoryDto;
import com.sunbeam.tikito.dto.CancelTicketDto;
import com.sunbeam.tikito.dto.CheckSeatDto;
import com.sunbeam.tikito.dto.TicketBookedDto;
import com.sunbeam.tikito.dto.TicketBookingDto;
import com.sunbeam.tikito.dto.UserBookingDto;
import com.sunbeam.tikito.entity.SeatEntity;
import com.sunbeam.tikito.entity.UserEntity;

public interface BookingService
{
	TicketBookedDto bookTicket(TicketBookingDto dto, long userId);
	CancelTicketDto cancelTicket(long bookingId, long userId);
	UserBookingDto getBookingsByUser(long bookingId, long userId);
	List<UserBookingDto> getAllBookingsByUser(long userId);
	List<AllBookingsDto> getAllBookingsByShow(long showId);
	List<AvailableSeatsDto> getAllAvailableSeats(long showId);
	public List<BookingHistoryDto> getBookingHistory(long userId);
	boolean areSeatsAvailable(CheckSeatDto dto);
}
