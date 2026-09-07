package com.sunbeam.tikito.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfig
{
	private final UserDetailsService userDetailsService;
	private final JwtFilter jwtFilter;
	
	public SecurityConfig(UserDetailsService userDetailsService, JwtFilter jwtFilter)
	{
		this.userDetailsService = userDetailsService;
		this.jwtFilter = jwtFilter;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http) throws Exception
	{
		AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		
		return authManagerBuilder.build();
	}
	
	@Bean
	SecurityFilterChain authorizeRequests(HttpSecurity http) throws Exception
	{
		http.csrf(csrf -> csrf.disable())
		    .cors(cors -> { })
			.authorizeHttpRequests(requests -> requests
								   .requestMatchers("/tikito/test/**").permitAll()
								   .requestMatchers("/tikito/auth/**","/tikito/user/register", "/tikito/user/forgot-password", "/tikito/user/reset-password").permitAll()
								   .requestMatchers(HttpMethod.GET,"/tikito/booking/admin/getAvailableSeats").hasAnyRole("USER", "ADMIN")
								   .requestMatchers("/tikito/admin/register").permitAll()
								   .requestMatchers("/tikito/booking/user/**").hasRole("USER")
								   .requestMatchers("/tikito/booking/admin/**").hasRole("ADMIN")
								   .requestMatchers("/tikito/user/**").hasRole("USER")
								   .requestMatchers(HttpMethod.POST, "/tikito/events/**").hasRole("ADMIN")
								   .requestMatchers(HttpMethod.PUT, "/tikito/events/**").hasRole("ADMIN")
								   .requestMatchers(HttpMethod.DELETE, "/tikito/events/**").hasRole("ADMIN")
								   .requestMatchers(HttpMethod.GET, "/tikito/events/**").permitAll()
								   .requestMatchers("/tikito/venue", "/tikito/venue/*", "/tikito/venue/name/*", "/tikito/venue/address/*").permitAll()
								   .requestMatchers("/tikito/venue/admin/**").hasRole("ADMIN")
								   .requestMatchers("/tikito/shows", "/tikito/shows/*", "/tikito/shows/event/*", "/tikito/shows/date/*", "/tikito/shows/time/*").permitAll()
								   .requestMatchers("/tikito/shows/admin/**").hasRole("ADMIN")
								   .requestMatchers("/posters/**").permitAll()
								   .requestMatchers("/profiles/**").permitAll()
								   .requestMatchers("/tikito/upload/**").permitAll()
								   .requestMatchers(HttpMethod.GET, "/tikito/foods/**").permitAll()
								   .requestMatchers("/tikito/foods/admin/**").hasRole("ADMIN")
								   //.requestMatchers("/tikito/payment/**").hasRole("USER")
								   .anyRequest().authenticated())
								   .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
								   .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		    
		
		return http.build();
	}
	
	@Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        //configuration.setAllowedOrigins(List.of("http://localhost:5175"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
}
}
