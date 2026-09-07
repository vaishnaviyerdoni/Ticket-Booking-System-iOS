package com.sunbeam.tikito.dto;

import lombok.Data;

@Data
public class VenueDto {

    private String name;

    private String address;

    private boolean isAreFacilitiesAvailable;
    
    private Integer seatCapacity;

}
