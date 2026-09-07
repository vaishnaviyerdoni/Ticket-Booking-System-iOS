package com.sunbeam.tikito.services;

import java.util.List;

import com.sunbeam.tikito.dto.FoodRequestDto;
import com.sunbeam.tikito.dto.FoodResponseDto;

public interface FoodService {

	List<FoodResponseDto> findAllFoods();

	FoodResponseDto findFoodById(Long foodId);

	List<FoodResponseDto> findFoodByName(String foodName);

	List<FoodResponseDto> findAvailableFoods();

	FoodResponseDto saveFood(FoodRequestDto dto);

	FoodResponseDto updateFood(Long foodId, FoodRequestDto dto);

	void deleteFoodById(Long foodId);
}