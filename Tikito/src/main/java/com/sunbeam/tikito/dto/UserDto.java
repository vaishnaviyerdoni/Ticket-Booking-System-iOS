package com.sunbeam.tikito.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {
	private Long userId;
	private String firstName;
	private String lastName;
	private String email;
	private Integer otp; //added for otp verification
	private String password;
	private String phone;
	private String role;
	private String newPassword;
	private String oldPassword;
	private String imageName;
	

}
