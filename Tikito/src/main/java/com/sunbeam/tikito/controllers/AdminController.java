package com.sunbeam.tikito.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunbeam.tikito.dto.UserDto;
import com.sunbeam.tikito.serviceimpl.AdminServiceImpl;
import com.sunbeam.tikito.utils.Resp;

@RequestMapping("/tikito/admin")
@RestController

public class AdminController {
	 private AdminServiceImpl adminService;

	    public AdminController(AdminServiceImpl adminService) {
	    	this.adminService = adminService;
	    }

	    @PostMapping("/register")
	    public Resp<?> registerAdmin(
	            @RequestBody UserDto dto) {
	        dto = adminService.registerAdmin(dto);
	        return Resp.success(dto);
	    }

//	    @PostMapping("/login")
//	    public Resp<?> loginAdmin(
//	            @RequestBody UserDto dto) {
//	        dto = adminService.loginAdmin(dto);
//	        return Resp.success(dto);
//	    }

}
