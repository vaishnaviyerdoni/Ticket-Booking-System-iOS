package com.sunbeam.tikito.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodRequestDto {

    private String foodName;

    private String description;

    private String imageUrl;

    private Double price;

    private boolean available;
}