package com.sunbeam.tikito.serviceimpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.sunbeam.tikito.daos.SeatDao;
import com.sunbeam.tikito.daos.VenueDao;
import com.sunbeam.tikito.dto.VenueDto;
import com.sunbeam.tikito.entity.SeatEntity;
import com.sunbeam.tikito.entity.VenueEntity;
import com.sunbeam.tikito.services.VenueService;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class VenueServiceImpl implements VenueService{
	
	private final VenueDao venueDao;
	private final SeatDao seatDao;
	private final ModelMapper modelMapper;
	
	
	

	public VenueServiceImpl(VenueDao venueDao, SeatDao seatDao, ModelMapper modelMapper) {
		super();
		this.venueDao = venueDao;
		this.seatDao = seatDao;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<VenueEntity> findAllVenues() {
		
		return venueDao.findAll();
	}

	@Override
	public VenueEntity findVenueById(Long venueId) {
		
		return venueDao.findById(venueId).orElseThrow(()-> new RuntimeException("Venue Not Found"));
	}

	@Override
	public VenueEntity saveVenue(VenueDto dto) {
		
		VenueEntity venue = modelMapper.map(dto, VenueEntity.class);
		
		 venue = venueDao.save(venue);
		 
		 for(int i=1 ; i <= venue.getSeatCapacity(); i++) {
			 
			 SeatEntity seat = new SeatEntity();
			 
			 seat.setVenue(venue);
			 seat.setSeatNo("A"+i);
			 
			 seatDao.save(seat);
			 
		 }
		
		
		
		return venue;
	}

	@Override
	public VenueEntity updateVenue(Long venueId, VenueDto dto) {
		
	VenueEntity venue = venueDao.findById(venueId).orElseThrow(()-> new RuntimeException("Venue not found"));
	
	venue.setName(dto.getName());
	venue.setAddress(dto.getAddress());
	venue.setSeatCapacity(dto.getSeatCapacity());
	venue.setAreFacilitiesAvailable(dto.isAreFacilitiesAvailable());
		
		
		return venueDao.save(venue);
	}

	@Override
	public void deleteVenueById(Long venueId) {
		
	 VenueEntity venue = venueDao.findById(venueId).orElseThrow(()-> new RuntimeException("Venue not found"));
	 
	 venueDao.delete(venue);
		
	}

	@Override
	public List<VenueEntity> findByName(String name) {
		
		
		return venueDao.findByNameContainingIgnoreCase(name);
	}

	@Override
	public List<VenueEntity> findByAddress(String address) {
		// TODO Auto-generated method stub
		return venueDao.findByAddressContainingIgnoreCase(address);
	}
	
	

}
