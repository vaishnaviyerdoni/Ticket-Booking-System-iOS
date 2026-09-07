package com.sunbeam.tikito.services;

import java.util.List;

import com.sunbeam.tikito.dto.VenueDto;
import com.sunbeam.tikito.entity.VenueEntity;

public interface VenueService {
	
	public List<VenueEntity> findAllVenues();
	
	
	public VenueEntity findVenueById(Long id);
	
	public VenueEntity saveVenue(VenueDto dto);
	
	public VenueEntity updateVenue(Long venueId, VenueDto dto);
	
	public void deleteVenueById(Long venueId);
	
	public List<VenueEntity> findByName(String name);
	
	public List<VenueEntity> findByAddress(String address);
	
	

}
