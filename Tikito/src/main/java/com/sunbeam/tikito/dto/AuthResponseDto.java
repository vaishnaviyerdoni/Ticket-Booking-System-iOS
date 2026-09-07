package com.sunbeam.tikito.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthResponseDto 
{
	private String jwtToken;
	private Long userId;
	private String email;
	private String firstName;
	private String lastName;
	private String role;
}
