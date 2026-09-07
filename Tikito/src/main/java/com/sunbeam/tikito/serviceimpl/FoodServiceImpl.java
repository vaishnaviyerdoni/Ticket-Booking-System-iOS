package com.sunbeam.tikito.serviceimpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.sunbeam.tikito.daos.FoodDao;
import com.sunbeam.tikito.dto.FoodRequestDto;
import com.sunbeam.tikito.dto.FoodResponseDto;
import com.sunbeam.tikito.entity.FoodEntity;
import com.sunbeam.tikito.services.FoodService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FoodServiceImpl implements FoodService {

	private final FoodDao foodDao;
	private final ModelMapper modelMapper;

	public FoodServiceImpl(FoodDao foodDao, ModelMapper modelMapper) {
		this.foodDao = foodDao;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<FoodResponseDto> findAllFoods() {

		List<FoodEntity> list = foodDao.findAll();

		return list.stream()
				.map(food -> modelMapper.map(food, FoodResponseDto.class))
				.toList();
	}

	@Override
	public FoodResponseDto findFoodById(Long foodId) {

		FoodEntity food = foodDao.findById(foodId)
				.orElseThrow(() -> new RuntimeException("Food not found"));

		return modelMapper.map(food, FoodResponseDto.class);
	}

	@Override
	public List<FoodResponseDto> findFoodByName(String foodName) {

		List<FoodEntity> list =
				foodDao.findByFoodNameContainingIgnoreCase(foodName);

		return list.stream()
				.map(food -> modelMapper.map(food, FoodResponseDto.class))
				.toList();
	}

	@Override
	public List<FoodResponseDto> findAvailableFoods() {

		List<FoodEntity> list = foodDao.findByAvailable(true);

		return list.stream()
				.map(food -> modelMapper.map(food, FoodResponseDto.class))
				.toList();
	}

	@Override
	public FoodResponseDto saveFood(FoodRequestDto dto) {

		FoodEntity food = modelMapper.map(dto, FoodEntity.class);

		food = foodDao.save(food);

		return modelMapper.map(food, FoodResponseDto.class);
	}

	@Override
	public FoodResponseDto updateFood(Long foodId, FoodRequestDto dto) {

		FoodEntity food = foodDao.findById(foodId)
				.orElseThrow(() -> new RuntimeException("Food not found"));

		food.setFoodName(dto.getFoodName());
		food.setDescription(dto.getDescription());
		food.setImageUrl(dto.getImageUrl());
		food.setPrice(dto.getPrice());
		food.setAvailable(dto.isAvailable());

		food = foodDao.save(food);

		return modelMapper.map(food, FoodResponseDto.class);
	}

	@Override
	public void deleteFoodById(Long foodId) {

		FoodEntity food = foodDao.findById(foodId)
				.orElseThrow(() -> new RuntimeException("Food not found"));

		foodDao.delete(food);
	}
}