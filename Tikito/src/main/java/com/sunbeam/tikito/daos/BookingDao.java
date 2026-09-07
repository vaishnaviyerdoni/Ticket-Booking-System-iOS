package com.sunbeam.tikito.daos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbeam.tikito.entity.BookingEntity;

public interface BookingDao extends JpaRepository<BookingEntity, Long>
{
	List<BookingEntity> findByUserUserId(Long userId);
	Optional<BookingEntity> findByBookingIdAndUserUserId(Long bookingId, Long userId);
	List<BookingEntity> findByShowShowId(Long showId);
}
