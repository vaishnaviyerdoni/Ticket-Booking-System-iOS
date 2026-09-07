package com.sunbeam.tikito.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbeam.tikito.entity.BookedSeatsEntity;

public interface BookedSeatsDao extends JpaRepository<BookedSeatsEntity, Long>
{
	List<BookedSeatsEntity> findByBookingBookingId(Long bookingId);

	void deleteByBookingBookingId(Long bookingId);

	boolean existsByShowShowIdAndSeatSeatId(Long showId, Long SeatId);

	List<BookedSeatsEntity> findByShowShowIdAndSeatSeatIdIn(Long showId, List<Long> seatIds);

	List<BookedSeatsEntity> findByShowShowId(Long showId);
}
