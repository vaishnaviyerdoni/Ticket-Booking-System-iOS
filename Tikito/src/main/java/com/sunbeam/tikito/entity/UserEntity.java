package com.sunbeam.tikito.entity;

    import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import jakarta.persistence.PrePersist;
	import jakarta.persistence.PreUpdate;
	import jakarta.persistence.Table;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

	
	@Entity
	@Table(name = "users")
    @NoArgsConstructor
    @AllArgsConstructor
	@Data
	//@ToString(exclude == "")
	public class UserEntity implements UserDetails {
	    
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name="user_id")
	    private Long userId;
	   
	    @Column(name = "first_name", nullable = false)
	    private String firstName;

	    @Column(name = "last_name", nullable = false)
	    private String lastName;
	    
	    @Column(name = "birth_date")
	    private LocalDate birthdate;

	    @Column(name = "email", unique = true, nullable = false)
	    private String email;


	    @Column(name = "password", nullable = false)
	    private String password;

	    @Column(name = "phone", unique = true, nullable = false)
	    private String phone;

	    @Column(name = "role", nullable = false)
	    private String role;
        
	    @Column(name = "image_name")
	    private String imageName;
        
	    @Column(name = "created_at", updatable = false)
	    private LocalDateTime createdAt;
        
	    @Column(name = "updated_at")
	    private LocalDateTime updatedAt;
	    
	    @PrePersist
	    public void onCreate() {
	    createdAt = LocalDateTime.now();
	    updatedAt = LocalDateTime.now();
	}
	    @PreUpdate
	    public void onUpdate() {
	    updatedAt = LocalDateTime.now();
	}
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			List<GrantedAuthority> authorities =AuthorityUtils.createAuthorityList(this.role);
			return authorities;
		}
		@Override
		public String getUsername() {
			return this.email;
		}

	    
	}


