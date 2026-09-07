package com.sunbeam.tikito.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.sunbeam.tikito.dto.ShowDto;
import com.sunbeam.tikito.dto.ShowResponseDto;
import com.sunbeam.tikito.dto.VenueShowsDto;
import com.sunbeam.tikito.entity.ShowEntity;

public interface ShowService {

	public List<ShowEntity> findAllShows();

	public ShowEntity findShowById(Long showId);

	public ShowEntity saveShow(ShowDto dto);

	public ShowEntity updateShow(Long showId, ShowDto dto);

	public void deleteShowById(Long showId);

	public List<ShowEntity> findByEvent(Long eventId);

	public List<ShowEntity> findByDate(LocalDate showDate);

	public List<ShowEntity> findByTime(LocalTime showStartTime);

	

	public ShowResponseDto getShowsByEvent(Long eventId);
	
}