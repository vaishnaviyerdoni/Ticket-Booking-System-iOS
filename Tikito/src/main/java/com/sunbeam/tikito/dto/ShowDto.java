package com.sunbeam.tikito.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class ShowDto {

	private Long venueId;

	private Long eventId;

	private String language;

	private Double price;

	private boolean isEighteenPlus;

	private LocalDate showDate;

	private LocalTime showStartTime;

	private LocalTime showEndTime;
}