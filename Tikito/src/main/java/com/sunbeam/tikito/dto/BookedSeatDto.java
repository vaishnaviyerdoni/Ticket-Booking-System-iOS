package com.sunbeam.tikito.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookedSeatDto 
{
	private Long bookedSeatsId;
	private Long bookingId;
	private Long seatId;
	private Long showId;
	
}
