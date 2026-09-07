package com.sunbeam.tikito.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"booking", "seat", "show"})
@Table(name="booked_seats",
	   uniqueConstraints = 
	   {
			@UniqueConstraint(name = "uk_show_seat",
							  columnNames = {"show_id", "seat_id"})
	   })
public class BookedSeatsEntity 
{
	@Id
	@Column(name="booked_seat_id", nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long bookedSeatsId;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="booking_id", nullable=false)
	private BookingEntity booking;
	
	@ManyToOne
	@JoinColumn(name="seat_id", nullable=false)
	private SeatEntity seat;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="show_id", nullable=false)
	private ShowEntity show;
	
	private LocalDateTime createdAt;
	
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

	public BookedSeatsEntity(Long bookedSeatsId, BookingEntity booking, SeatEntity seat, ShowEntity show) {
		this.bookedSeatsId = bookedSeatsId;
		this.booking = booking;
		this.seat = seat;
		this.show = show;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookedSeatsId == null) ? 0 : bookedSeatsId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof BookedSeatsEntity))
			return false;
		BookedSeatsEntity other = (BookedSeatsEntity) obj;
		if (bookedSeatsId == null) {
			if (other.bookedSeatsId != null)
				return false;
		} else if (!bookedSeatsId.equals(other.bookedSeatsId))
			return false;
		return true;
	}
}
