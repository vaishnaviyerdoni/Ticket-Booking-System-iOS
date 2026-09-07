package com.sunbeam.tikito.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DateDto {

    private LocalDate showDate;

    private String day;

    private String month;

}