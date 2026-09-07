package com.sunbeam.tikito.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbeam.tikito.entity.VenueEntity;

//changed Integer to Long
public interface VenueDao extends JpaRepository<VenueEntity, Long>  {
	
List<VenueEntity> findByNameContainingIgnoreCase(String name);
List<VenueEntity> findByAddressContainingIgnoreCase(String address);

}
