package com.sunbeam.tikito.security;

import javax.crypto.SecretKey;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.sunbeam.tikito.entity.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil
{
	@Value("${jwt.secret}")
	private String jwtSecret;
	
	@Value("${jwt.expiration}")
	private long jwtExpiry;
	
	private SecretKey key;
	
	@PostConstruct
	public void init()
	{
		key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
	}
	
	//create the jwt token
	public String createToken(Authentication authentication)
	{
		UserEntity user = (UserEntity) authentication.getPrincipal();
		
		String token = Jwts.builder()
					   .subject(user.getEmail())
					   .claim("role", user.getRole())
					   .issuedAt(new Date())
					   .expiration(new Date(System.currentTimeMillis() + jwtExpiry))
					   .signWith(key)
					   .compact();
		
		return token;
	}
	
	//create claims - claims is data or payload stored in jwt token
	private Claims extractClaims(String token)
	{
		Claims claims = Jwts.parser()
						.verifyWith(key)
						.build()
						.parseSignedClaims(token)
						.getPayload();
		
		return claims;
	}
	
	//Here user name is email
	public String extractUsername(String token)
	{
		//subject is email
		String email = extractClaims(token).getSubject();
		return email;
	}
	
	//role tells whether user is admin or just a user 
	public String getRole(String token)
	{
		String role = extractClaims(token).get("role", String.class);
		return role;
	}
	
	//token expiration date
	public Date extractTokenExpiration(String token)
	{
		Date expiry = extractClaims(token).getExpiration();
		return expiry;
	}
	
	//Creating and returning obj of Authentiacation
	public Authentication getAuthentication(String token, UserDetails details)
	{
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
		return auth;
	}
}