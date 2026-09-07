package com.sunbeam.tikito.dto;

import java.util.List;

import lombok.Data;

@Data
public class CheckSeatDto {

    private Long showId;

    private List<Long> seatIds;
}