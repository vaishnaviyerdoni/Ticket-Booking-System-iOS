package com.sunbeam.tikito.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
import com.sunbeam.tikito.serviceimpl.BookingServiceImpl;
import com.sunbeam.tikito.services.BookingService;
import com.sunbeam.tikito.utils.Resp;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tikito/booking")
public class BookingController 
{
	@Autowired
	private BookingService ser;
	
	@PostMapping("/user")
	public Resp<?> bookTicket(@Valid @RequestBody TicketBookingDto dto, @AuthenticationPrincipal UserEntity loggedInUser)
	{
		TicketBookedDto ticket = ser.bookTicket(dto, loggedInUser.getUserId());
		return Resp.success(ticket);
	}
	
	@PatchMapping("/user/cancel/{bookingId}")
	public Resp<?> cancelTicket(@PathVariable long bookingId, @AuthenticationPrincipal UserEntity loggedInUser)
	{
		CancelTicketDto cancelledTicket = ser.cancelTicket(bookingId, loggedInUser.getUserId());
		return Resp.success(cancelledTicket);
	}
	
	@GetMapping("/user/mybooking/{bookingId}")//returns single booking
	public Resp<?> getBookingsByUser(@PathVariable long bookingId, @AuthenticationPrincipal UserEntity loggedInUser)
	{
		UserBookingDto userDetails = ser.getBookingsByUser(bookingId, loggedInUser.getUserId());
		return Resp.success(userDetails);
	}
	
	@GetMapping("/user/getMyBooking") //My tickets in ui //returns all booking of a user
	public Resp<?> getAllBookingsByUser( @AuthenticationPrincipal UserEntity loggedInUser)
	{
		List<UserBookingDto> userDetailsList = ser.getAllBookingsByUser(loggedInUser.getUserId());
		return Resp.success(userDetailsList);
	}
	
	@GetMapping("/user/getMyBookings")
	public Resp<?> getAllBookingHistoryByUser(
	        @AuthenticationPrincipal UserEntity loggedInUser)
	{
	    List<BookingHistoryDto> bookingHistory =
	            ser.getBookingHistory(loggedInUser.getUserId());

	    return Resp.success(bookingHistory);
	}
	
	
	@GetMapping("/admin/getByShowId/{showId}")
	@PreAuthorize("hasRole('ADMIN')")
	public Resp<?> getAllBookingByShow(@PathVariable long showId)
	{
		List<AllBookingsDto> allBookings = ser.getAllBookingsByShow(showId);
		return Resp.success(allBookings);
	}
	
	@GetMapping("/admin/getAvailableSeats")
//	@PreAuthorize("hasRole('ADMIN')")
	public Resp<?> getAllAvailableSeats(@RequestParam long showId)
	{
		List<AvailableSeatsDto> availableSeats = ser.getAllAvailableSeats(showId);
		return Resp.success(availableSeats);
	}
	
	@PostMapping("/user/checkAvailability")
	public Resp<?> checkAvailability(
	        @RequestBody CheckSeatDto dto)
	{
	    boolean available =
	            ser.areSeatsAvailable(dto);

	    return Resp.success(available);
	}
}
