package com.sunbeam.tikito.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunbeam.tikito.dto.EventRequestDto;
import com.sunbeam.tikito.dto.EventResponseDto;
import com.sunbeam.tikito.dto.EventTypeCountDto;
import com.sunbeam.tikito.serviceimpl.EventServiceImpl;
import com.sunbeam.tikito.utils.Resp;

	@RestController
	@RequestMapping("/tikito/events")
	public class EventController {
//	@Value("${image.folder.path}")
//	private String imageFolderpath;
	private EventServiceImpl eventServiceImpl;
	
	public EventController(EventServiceImpl eventServiceImpl) {
		super();
		this.eventServiceImpl = eventServiceImpl;
	}
	
	@GetMapping
	public Resp<?> findAllEvents(){
		List<EventResponseDto> list=eventServiceImpl.findAllEvents();
		return Resp.success(list);
	}
	
	@GetMapping("/{eventId}")
	public Resp<?> findEventById(@PathVariable Long eventId) {
		EventResponseDto dto=eventServiceImpl.findEventById(eventId);
		return Resp.success(dto);
	}
	
	@GetMapping("/name/{eventName}")
	public Resp<?> findByName(@PathVariable String eventName) {
		List<EventResponseDto> list=eventServiceImpl.findByName(eventName);
		return Resp.success(list);
	}
	
	@GetMapping("/type/{eventType}")
	public Resp<?> findByEventType(@PathVariable String eventType) {
		List<EventResponseDto> list=eventServiceImpl.findByEventType(eventType);
		return Resp.success(list);
	}
	
	@GetMapping("/duration/{duration}")
	public Resp<?> findByDuration(@PathVariable Long duration) {
		List<EventResponseDto> list=eventServiceImpl.findByDuration(duration);
		return Resp.success(list);
	}
	
	@PostMapping
	public Resp<?> saveEvent(@RequestBody EventRequestDto dto) {
		EventResponseDto event=eventServiceImpl.saveEvent(dto);
		return Resp.success(event);
		
	}
	
	@PutMapping("/{eventId}")
	public Resp<?> updateEvent(@PathVariable Long eventId,@RequestBody EventRequestDto dto){
		EventResponseDto event=eventServiceImpl.updateEvent(eventId, dto);
		return Resp.success(event);
	}
	
	
	@DeleteMapping("/{eventId}")
	public Resp<String> deleteEvent(@PathVariable Long eventId) {
    eventServiceImpl.deleteEventById(eventId);
    return Resp.success("Event deleted successfully");
    }	
	
	@GetMapping("/count-by-type")
	public Resp<List<EventTypeCountDto>> getEventCountByType() {
	    return Resp.success(eventServiceImpl.getEventCountByType());
	}
}
