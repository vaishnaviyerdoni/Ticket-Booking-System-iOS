package com.sunbeam.tikito.serviceimpl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.sunbeam.tikito.dto.AuthRequestDto;
import com.sunbeam.tikito.dto.AuthResponseDto;
import com.sunbeam.tikito.entity.UserEntity;
import com.sunbeam.tikito.security.JwtUtil;
import com.sunbeam.tikito.services.AuthService;


@Service
public class AuthServiceImpl implements AuthService
{

	private AuthenticationManager authManager;
	private JwtUtil jwtUtil;
	
	public AuthServiceImpl(AuthenticationManager authManager, JwtUtil jwtUtil) 
	{
		this.authManager = authManager;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public AuthResponseDto login(AuthRequestDto request)
	{
		//create the authentication object
		Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		
		//create the jwt token
		String token = jwtUtil.createToken(authentication);
		
		//get the principal - principal is the authenticated user
		UserEntity user = (UserEntity)authentication.getPrincipal();
		
		//create response obj and return back
		AuthResponseDto res = new AuthResponseDto(token, user.getUserId(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getRole());
		
		return res;
	}

}
