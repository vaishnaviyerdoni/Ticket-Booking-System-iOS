package com.sunbeam.tikito.serviceimpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.sunbeam.tikito.daos.EventDao;
import com.sunbeam.tikito.dto.EventRequestDto;
import com.sunbeam.tikito.dto.EventResponseDto;
import com.sunbeam.tikito.dto.EventTypeCountDto;
import com.sunbeam.tikito.entity.EventEntity;
import com.sunbeam.tikito.services.EventService;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class EventServiceImpl implements EventService {
	
		private final EventDao eventDao;
		private final ModelMapper modelMapper;
		public EventServiceImpl(EventDao eventDao, ModelMapper modelMapper) {
		this.eventDao = eventDao;
		this.modelMapper = modelMapper;
		}
	
		public List<EventResponseDto> findAllEvents(){
			
		List<EventEntity> list= eventDao.findAll();
		List<EventResponseDto> dtoList=list.stream()
				.map(event ->modelMapper
				.map(event, EventResponseDto.class))
                .toList();
		return dtoList;
		}

		public EventResponseDto findEventById(Long eventId) {
			
		EventEntity event=eventDao
				          .findById(eventId)
				          .orElseThrow(() -> new RuntimeException("Event not found"));
		return modelMapper.map(event,EventResponseDto.class);
		}
		
		
	
		public EventResponseDto saveEvent(EventRequestDto dto) {
			
		EventEntity event=modelMapper.map(dto, EventEntity.class);
		event=eventDao.save(event);
		return modelMapper.map(event, EventResponseDto.class);
		}
	
		public EventResponseDto updateEvent(Long eventId,EventRequestDto dto) {
		EventEntity event=eventDao
				          .findById(eventId)
				          .orElseThrow(() -> new RuntimeException("Event not found"));
		event.setEventName(dto.getEventName());
	    event.setEventDescription(dto.getEventDescription());
	    event.setEventType(dto.getEventType());
	    event.setEventDurationMin(dto.getEventDurationMin());
	    event.setAgeRestriction(dto.getAgeRestriction());
	    event.setPosterUrl(dto.getPosterUrl());
		event = eventDao.save(event);
		return modelMapper.map(event, EventResponseDto.class);
		}
	
	    public void deleteEventById(Long eventId) {
		EventEntity event =eventDao
				           .findById(eventId)
				           .orElseThrow(() -> new RuntimeException("Event not found"));
		eventDao.delete(event);
		}
	
	    public List<EventResponseDto> findByName(String eventName) {

		List<EventEntity> list= eventDao.findByEventNameContainingIgnoreCase(eventName);
	    List<EventResponseDto> dtoList=list.stream()
	                                       .map(event -> modelMapper.map(
	                                        event,
	                                        EventResponseDto.class))
	                                       .toList();
		return dtoList;
	    }

	    public List<EventResponseDto> findByEventType(String eventType) {

	    List<EventEntity> list= eventDao.findByEventTypeIgnoreCase(eventType);
		List<EventResponseDto> dtoList=list
	                					   .stream()
	                					   .map(event -> modelMapper.map(
	                					    event,
	                					   EventResponseDto.class))
	                					   .toList();
			
		return dtoList;
	    }

	    public List<EventResponseDto> findByDuration(Long duration) {

	    List<EventEntity> list= eventDao.findByEventDurationMin(duration);
		List<EventResponseDto> dtoList=list
	                					   .stream()
	                					   .map(event -> modelMapper.map(
	                					    event,
	                					    EventResponseDto.class))
	                					   .toList();
		return dtoList;
	    }
	
	    @Override
	    public List<EventTypeCountDto> getEventCountByType() {
	        return eventDao.getEventCountByType();
	    }
}
