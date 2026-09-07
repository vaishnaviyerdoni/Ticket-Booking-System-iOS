package com.sunbeam.tikito.daos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbeam.tikito.entity.ShowEntity;

public interface ShowDao extends JpaRepository<ShowEntity, Long> {

	List<ShowEntity> findByEventEventId(Long eventId);

	List<ShowEntity> findByShowDate(LocalDate showDate);

	List<ShowEntity> findByShowStartTime(LocalTime showStartTime);
	List<ShowEntity> findByEvent_EventId(Long eventId);
	List<ShowEntity> findByEvent_EventIdOrderByShowDateAscShowStartTimeAsc(Long eventId);
}