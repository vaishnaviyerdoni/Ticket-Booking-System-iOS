package com.sunbeam.tikito.services;

import com.sunbeam.tikito.enums.OtpPurpose;

public interface OtpService 
{
	void generateAndSendOtp(String email, OtpPurpose purpose);
	boolean verifyOtp(String email, Integer otp, OtpPurpose purpose);
	void invalidateOtp(String email, OtpPurpose purpose);
}
