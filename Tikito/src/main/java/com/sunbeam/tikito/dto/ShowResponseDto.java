package com.sunbeam.tikito.dto;

import java.util.List;

import lombok.Data;

@Data
public class ShowResponseDto {

    private List<DateDto> dates;

    private List<VenueShowsDto> venues;

}