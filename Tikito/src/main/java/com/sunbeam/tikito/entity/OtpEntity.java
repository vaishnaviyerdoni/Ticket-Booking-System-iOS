package com.sunbeam.tikito.entity;

import java.time.LocalDateTime;

import com.sunbeam.tikito.enums.OtpPurpose;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="otp_verification")
@Getter
@Setter
@NoArgsConstructor
public class OtpEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="otp_id")
    private Long otpId;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private UserEntity user;

    @Column(name="otp", nullable=false)
    private Integer otp;

    @Enumerated(EnumType.STRING)
    @Column(name="purpose", nullable=false)
    private OtpPurpose otpPurpose;

    @Column(name="expiry_time", nullable=false)
    private LocalDateTime expiryTime;

    @Column(name="used", nullable=false)
    private boolean used;

    @Column(name="created_at", updatable=false)
    private LocalDateTime createdAt;

    @Column(name="updated_at")
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

	public OtpEntity(Long otpId, UserEntity user, Integer otp, OtpPurpose otpPurpose, LocalDateTime expiryTime,
			boolean used) {
		this.otpId = otpId;
		this.user = user;
		this.otp = otp;
		this.otpPurpose = otpPurpose;
		this.expiryTime = expiryTime;
		this.used = used;
	}
}
