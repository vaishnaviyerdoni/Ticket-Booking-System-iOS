package com.sunbeam.tikito.entity;


import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "events")
public class EventEntity {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "event_id")
		private Long eventId;
		
		@Column(name = "event_name", nullable = false)
		private String eventName;
		
		@Column(name = "event_type", nullable = false)
		private String eventType;
		
		@Column(name = "event_description", columnDefinition = "TEXT")
		private String eventDescription;
		
		@Column(name = "event_duration_min")
		private Long eventDurationMin;
		
		@Column(name = "age_restriction")
		private Integer ageRestriction;
		
		@Column(name = "poster_url")
		private String posterUrl;
		
		@CreationTimestamp
		@Column(name = "created_at", updatable = false)
		private LocalDateTime createdAt;
		
		@UpdateTimestamp
		@Column(name = "updated_at")
		private LocalDateTime updatedAt;
		
		
		@OneToMany(mappedBy = "event",
		           cascade = CascadeType.ALL,
				   fetch = FetchType.LAZY)
		private List<ShowEntity> shows;
}
