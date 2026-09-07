package com.sunbeam.tikito.daos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbeam.tikito.entity.OtpEntity;
import com.sunbeam.tikito.entity.UserEntity;
import com.sunbeam.tikito.enums.OtpPurpose;

public interface OtpDao extends JpaRepository<OtpEntity, Long>
{
	Optional<OtpEntity> findTopByUserAndOtpPurposeAndUsedFalseOrderByCreatedAtDesc(UserEntity user, OtpPurpose otpPurpose);
	 void deleteByUserAndOtpPurpose(UserEntity user, OtpPurpose otpPurpose);
}
