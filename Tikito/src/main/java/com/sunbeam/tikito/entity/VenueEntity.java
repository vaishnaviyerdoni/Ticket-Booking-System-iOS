package com.sunbeam.tikito.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


//@ToString(exclude = "")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "venues")
public class VenueEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "venue_id")
	private Long venueId; //changed id to venueId // ok

	@Column(name = "venue_name")
	private String name;

	@Column(name = "venue_address")
	private String address;
	
	@Column(name = "seat_capacity")
	private Integer seatCapacity;
  
	@Column(name = "are_facilities_available")
	private boolean isAreFacilitiesAvailable;

	@OneToMany(mappedBy = "venue" , fetch = FetchType.LAZY)
	private List<SeatEntity> seatList;

	@OneToMany(mappedBy = "venue", fetch = FetchType.LAZY)
	private List<ShowEntity> showList;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@PrePersist
	public void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	public void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

}
