package com.sunbeam.tikito.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sunbeam.tikito.entity.UserEntity;
import com.sunbeam.tikito.serviceimpl.CustomUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtFilter extends OncePerRequestFilter {
	
	
	private final JwtUtil jwtUtil;
	private final CustomUserDetailsService userDetailsService;
	
	
	




	public JwtFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
		super();
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}







	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			
		String authHeader = request.getHeader("Authorization");
			
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			
			String token = authHeader.substring(7);
			
			String email = jwtUtil.extractUsername(token);
			
			if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				
				UserEntity	user = (UserEntity) userDetailsService.loadUserByUsername(email);
			
				if(jwtUtil.getAuthentication(token, user) != null) {
					
				Authentication authentication	= jwtUtil.getAuthentication(token, user);
					
				SecurityContextHolder.getContext().setAuthentication(authentication);
					
				}
				
			}
			
		}
		
		filterChain.doFilter(request, response);
			
		} 
		
		catch (ExpiredJwtException e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"JWT token expired");
		}
		catch (Exception e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid JWT token");
		}
		
	}

}
