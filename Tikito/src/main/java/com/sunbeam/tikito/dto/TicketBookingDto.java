package com.sunbeam.tikito.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//request dto
public class TicketBookingDto
{
//	@NotNull(message = "UserId cannot be absent")
//	private Long userId;
	
	@NotNull(message = "ShowId cannot be absent")
	private Long showId;
	
	@NotEmpty(message = "Atleast one seat needs to be booked")
	private List<Long> seatIds; 
}
