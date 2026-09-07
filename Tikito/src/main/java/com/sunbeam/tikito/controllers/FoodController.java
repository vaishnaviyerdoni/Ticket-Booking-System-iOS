package com.sunbeam.tikito.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.sunbeam.tikito.dto.FoodRequestDto;
import com.sunbeam.tikito.dto.FoodResponseDto;
import com.sunbeam.tikito.serviceimpl.FoodServiceImpl;
import com.sunbeam.tikito.utils.Resp;

@RestController
@RequestMapping("/tikito/foods")
public class FoodController {

	private final FoodServiceImpl foodServiceImpl;

	public FoodController(FoodServiceImpl foodServiceImpl) {
		this.foodServiceImpl = foodServiceImpl;
	}

	@GetMapping
	public Resp<?> findAllFoods() {

		List<FoodResponseDto> list =
				foodServiceImpl.findAllFoods();

		return Resp.success(list);
	}

	@GetMapping("/{foodId}")
	public Resp<?> findFoodById(
			@PathVariable Long foodId) {

		FoodResponseDto dto =
				foodServiceImpl.findFoodById(foodId);

		return Resp.success(dto);
	}

	@GetMapping("/name/{foodName}")
	public Resp<?> findFoodByName(
			@PathVariable String foodName) {

		List<FoodResponseDto> list =
				foodServiceImpl.findFoodByName(foodName);

		return Resp.success(list);
	}

	@GetMapping("/available")
	public Resp<?> findAvailableFoods() {

		List<FoodResponseDto> list =
				foodServiceImpl.findAvailableFoods();

		return Resp.success(list);
	}

	@PostMapping("/admin")
	public Resp<?> saveFood(
			@RequestBody FoodRequestDto dto) {

		FoodResponseDto food =
				foodServiceImpl.saveFood(dto);

		return Resp.success(food);
	}

	@PutMapping("/admin/{foodId}")
	public Resp<?> updateFood(
			@PathVariable Long foodId,
			@RequestBody FoodRequestDto dto) {

		FoodResponseDto food =
				foodServiceImpl.updateFood(foodId, dto);

		return Resp.success(food);
	}

	@DeleteMapping("/admin/{foodId}")
	public Resp<String> deleteFood(
			@PathVariable Long foodId) {

		foodServiceImpl.deleteFoodById(foodId);

		return Resp.success("Food deleted successfully");
	}
}