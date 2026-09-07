package com.sunbeam.tikito.services;

import java.util.List;

import com.sunbeam.tikito.dto.EventRequestDto;
import com.sunbeam.tikito.dto.EventResponseDto;
import com.sunbeam.tikito.dto.EventTypeCountDto;

public interface EventService {

	public List<EventResponseDto> findAllEvents();
	
	public EventResponseDto findEventById(Long eventId);
	
	public EventResponseDto saveEvent(EventRequestDto dto) ;
	
	public EventResponseDto updateEvent(Long eventId,EventRequestDto dto);
	
	public void deleteEventById(Long eventId);
	
	  public List<EventResponseDto> findByName(String eventName) ;
	  
	  public List<EventResponseDto> findByEventType(String eventType);
	  
	  public List<EventResponseDto> findByDuration( Long duration);
	  public List<EventTypeCountDto> getEventCountByType();
}
