package com.sunbeam.tikito.services;

import org.springframework.web.multipart.MultipartFile;

import com.sunbeam.tikito.dto.UserDto;

public interface UserService {
	UserDto register(UserDto dto);
//	UserDto login(UserDto dto);
	UserDto getProfile(Long userId);
	String updatePassword(Long userId, UserDto dto);
	String forgotPassword(UserDto dto);
	String deleteAccount(Long userId);
	String updateProfileImage(Long userId, UserDto dto);
	public String resetPassword(UserDto dto);
	public String updateProfile(Long userId, UserDto dto);
}	
//	String forgotPassword(String email,String newPassword);


