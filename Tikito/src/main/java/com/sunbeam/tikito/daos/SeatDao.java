package com.sunbeam.tikito.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.sunbeam.tikito.entity.SeatEntity;

import jakarta.persistence.LockModeType;

public interface SeatDao extends JpaRepository<SeatEntity,Long> 
{
	
	//method needed for booking Seats
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<SeatEntity> findBySeatIdIn(List<Long> seatIds);
}
