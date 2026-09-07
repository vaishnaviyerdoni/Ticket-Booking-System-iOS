package com.sunbeam.tikito.controllers;
		
		import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
		import org.springframework.web.bind.annotation.GetMapping;
		import org.springframework.web.bind.annotation.PathVariable;
		import org.springframework.web.bind.annotation.PostMapping;
		import org.springframework.web.bind.annotation.PutMapping;
		import org.springframework.web.bind.annotation.RequestBody;
		import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sunbeam.tikito.dto.UserDto;
import com.sunbeam.tikito.entity.UserEntity;
import com.sunbeam.tikito.serviceimpl.UserServiceImpl;
import com.sunbeam.tikito.services.UserService;
import com.sunbeam.tikito.utils.Resp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;


@RestController
@RequestMapping("/tikito/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Public
    @PostMapping("/register")
    public Resp<?> register(@RequestBody UserDto dto) {

        return Resp.success(userService.register(dto));
    }

    // Logged-in user
    @GetMapping("/profile")
    public Resp<?> getProfile(
            @AuthenticationPrincipal UserEntity loggedInUser) {

        return Resp.success(
                userService.getProfile(loggedInUser.getUserId()));
    }
    
    @PutMapping("/profile")
    public Resp<?> updateProfile(
            @RequestBody UserDto dto,
            @AuthenticationPrincipal UserEntity loggedInUser){

        return Resp.success(
                userService.updateProfile(
                        loggedInUser.getUserId(),
                        dto));
    }
    

    // Logged-in user
    @PutMapping("/password")
    public Resp<?> updatePassword(
            @RequestBody UserDto dto,
            @AuthenticationPrincipal UserEntity loggedInUser) {

        return Resp.success(
                userService.updatePassword(
                        loggedInUser.getUserId(),
                        dto));
    }

    // Public
    @PostMapping("/forgot-password")
    public Resp<?> forgotPassword(
            @RequestBody UserDto dto) {

        return Resp.success(
                userService.forgotPassword(dto));
    }
    
    //public 
    @PostMapping("/reset-password")
    public Resp<?> resetPassword(
            @RequestBody UserDto dto)
    {
        return Resp.success(
                userService.resetPassword(dto));
    }

    // Logged-in user
    @DeleteMapping
    public Resp<?> deleteAccount(
            @AuthenticationPrincipal UserEntity loggedInUser) {

        return Resp.success(
                userService.deleteAccount(
                        loggedInUser.getUserId()));
    }
    
    @PutMapping("/profile-image")
    public Resp<?> updateProfileImage(
            @RequestBody UserDto dto,
            @AuthenticationPrincipal UserEntity loggedInUser) {

        return Resp.success(
                userService.updateProfileImage(
                        loggedInUser.getUserId(),
                        dto));
    }
}

