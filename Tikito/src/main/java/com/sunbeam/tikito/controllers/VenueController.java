package com.sunbeam.tikito.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunbeam.tikito.dto.VenueDto;
import com.sunbeam.tikito.entity.VenueEntity;
import com.sunbeam.tikito.serviceimpl.VenueServiceImpl;
import com.sunbeam.tikito.services.VenueService;
import com.sunbeam.tikito.utils.Resp;

@RestController
@RequestMapping("/tikito/venue")
public class VenueController {
	
	
 private VenueServiceImpl venueServiceImpl;

 
public VenueController(VenueServiceImpl venueServiceImpl) {
	super();
	this.venueServiceImpl = venueServiceImpl;
}
 
 @GetMapping
 public Resp<?> findAllVenues(){
	 
	  List<VenueEntity> list = venueServiceImpl.findAllVenues();
	return Resp.success(list);
 }
 
 @GetMapping("/{venueId}")
 public Resp<?> findVenueById(@PathVariable Long venueId){
	 
	 	
	  VenueEntity venue = venueServiceImpl.findVenueById(venueId);
	 
	 
	return Resp.success(venue);
	 
	 
 }
 
 
 @GetMapping("/name/{name}")
	public Resp<?> findByName(@PathVariable String name) {

		List<VenueEntity> list = venueServiceImpl.findByName(name);

		return Resp.success(list);
	}

	@GetMapping("/address/{address}")
	public Resp<?> findByAddress(@PathVariable String address) {

		List<VenueEntity> list = venueServiceImpl.findByAddress(address);

		return Resp.success(list);
	}

	@PostMapping("/admin")
	public Resp<?> saveVenue(@RequestBody VenueDto dto) {

		VenueEntity venue = venueServiceImpl.saveVenue(dto);

		return Resp.success(venue);
	}

	@PutMapping("/admin/{venueId}")
	public Resp<?> updateVenue(@PathVariable Long venueId,
			                   @RequestBody VenueDto dto) {

		VenueEntity venue = venueServiceImpl.updateVenue(venueId, dto);

		return Resp.success(venue);
	}

	@DeleteMapping("/admin/{venueId}")
	public Resp<String> deleteVenue(@PathVariable Long venueId) {

		venueServiceImpl.deleteVenueById(venueId);

		return Resp.success("Venue deleted successfully");
	}
 
 





}
