package com.sunbeam.tikito.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbeam.tikito.entity.FoodEntity;

public interface FoodDao extends JpaRepository<FoodEntity, Long> {

	List<FoodEntity> findByFoodNameContainingIgnoreCase(String foodName);

	List<FoodEntity> findByAvailable(boolean available);
}