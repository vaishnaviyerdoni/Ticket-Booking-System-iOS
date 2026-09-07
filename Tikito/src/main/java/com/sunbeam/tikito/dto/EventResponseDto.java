package com.sunbeam.tikito.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventResponseDto {
	private Long eventId;

    private String eventName;

    private String eventType;
    

    private String eventDescription;

    private Long eventDurationMin;

    private Integer ageRestriction;

    private String posterUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
