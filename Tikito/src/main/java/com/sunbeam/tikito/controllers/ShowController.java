package com.sunbeam.tikito.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sunbeam.tikito.dto.ShowDto;
import com.sunbeam.tikito.dto.ShowResponseDto;
import com.sunbeam.tikito.dto.VenueShowsDto;
import com.sunbeam.tikito.entity.ShowEntity;
import com.sunbeam.tikito.serviceimpl.ShowServiceImpl;
import com.sunbeam.tikito.utils.Resp;

@RestController
@RequestMapping("/tikito/shows")
public class ShowController {

	private ShowServiceImpl showServiceImpl;

	public ShowController(ShowServiceImpl showServiceImpl) {
		this.showServiceImpl = showServiceImpl;
	}

	@GetMapping
	public Resp<?> findAllShows() {

		List<ShowEntity> list = showServiceImpl.findAllShows();

		return Resp.success(list);
	}

	@GetMapping("/{showId}")
	public Resp<?> findShowById(@PathVariable Long showId) {

		ShowEntity show = showServiceImpl.findShowById(showId);

		return Resp.success(show);
	}
//
//	@GetMapping("/event/{eventId}")
//	public Resp<?> findShowByEvent(@PathVariable Long eventId) {
//
//		List<ShowEntity> list = showServiceImpl.findByEvent(eventId);
//
//		return Resp.success(list);
//	}

	@GetMapping("/date/{showDate}")
	public Resp<?> findByDate(@PathVariable LocalDate showDate) {

		List<ShowEntity> list = showServiceImpl.findByDate(showDate);

		return Resp.success(list);
	}

	@GetMapping("/time/{showTime}")
	public Resp<?> findByTime(@PathVariable LocalTime showTime) {

		List<ShowEntity> list = showServiceImpl.findByTime(showTime);

		return Resp.success(list);
	}

	@PostMapping("/admin")
	public Resp<?> saveShow(@RequestBody ShowDto dto) {

		ShowEntity show = showServiceImpl.saveShow(dto);

		return Resp.success(show);
	}

	@PutMapping("/admin/{showId}")
	public Resp<?> updateShow(
			@PathVariable Long showId,
			@RequestBody ShowDto dto) {

		ShowEntity show = showServiceImpl.updateShow(showId, dto);

		return Resp.success(show);
	}

	@DeleteMapping("/admin/{showId}")
	public Resp<String> deleteShow(@PathVariable Long showId) {

		showServiceImpl.deleteShowById(showId);

		return Resp.success("Show deleted successfully");
	
	}
	
	@GetMapping("/event/{eventId}")
	public ResponseEntity<?> getShowsByEvent(
	        @PathVariable Long eventId) {

	    ShowResponseDto response =
	            showServiceImpl.getShowsByEvent(eventId);

	    return ResponseEntity.ok(
	            Resp.success(response)
	    );
	}
}