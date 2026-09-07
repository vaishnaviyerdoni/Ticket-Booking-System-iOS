package com.sunbeam.tikito.dto;

import java.util.List;

import lombok.Data;

@Data
public class VenueShowsDto {

    private Long venueId;

    private String venueName;

    private String address;

    private boolean areFacilitiesAvailable;

    private List<ShowTimingDto> shows;
}