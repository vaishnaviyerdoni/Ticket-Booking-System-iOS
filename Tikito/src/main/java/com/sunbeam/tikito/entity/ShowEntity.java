package com.sunbeam.tikito.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "shows")
public class ShowEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "show_id")
	private Long showId; //changed id to show Id // ok

	@Column(name="show_language")
	private String language;

	@Column(name = "is_eighteen_plus")
	private boolean isEighteenPlus;

	@Column(name = "show_date")
	private LocalDate showDate;

	@Column(name = "show_start_time")
	private LocalTime showStartTime;

	@Column(name = "show_end_time")
	private LocalTime showEndTime;

	@ManyToOne
	@JoinColumn(name = "venue_id")
	@JsonIgnore
	private VenueEntity venue;

	@ManyToOne
	@JoinColumn(name = "event_id")
	@JsonIgnore
	private EventEntity event ;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	@Column(name = "price", nullable=false)
	private Double price;

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
