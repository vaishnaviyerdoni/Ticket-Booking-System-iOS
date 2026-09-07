package com.sunbeam.tikito.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestDto {
	private String eventName;

    private String eventType;

    private String eventDescription;

    private Long eventDurationMin;

    private Integer ageRestriction;

    private String posterUrl;
}
