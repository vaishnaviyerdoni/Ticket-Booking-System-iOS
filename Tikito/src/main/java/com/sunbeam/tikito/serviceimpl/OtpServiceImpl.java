package com.sunbeam.tikito.serviceimpl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.sunbeam.tikito.daos.OtpDao;
import com.sunbeam.tikito.daos.UserDao;
import com.sunbeam.tikito.entity.OtpEntity;
import com.sunbeam.tikito.entity.UserEntity;
import com.sunbeam.tikito.enums.OtpPurpose;
import com.sunbeam.tikito.exceptions.UserNotFoundException;
import com.sunbeam.tikito.services.EmailService;
import com.sunbeam.tikito.services.OtpService;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OtpServiceImpl implements OtpService
{
	private OtpDao otpdao;
	private UserDao userdao;
	private EmailService emailService;
	
	

	public OtpServiceImpl(OtpDao otpdao, UserDao userdao, EmailService emailService) 
	{
		super();
		this.otpdao = otpdao;
		this.userdao = userdao;
		this.emailService = emailService;
	}

	@Override
	public void generateAndSendOtp(String email, OtpPurpose purpose)
	{
		//find user ny its email
		UserEntity user = userdao.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user not found"));
		
		//delete any old otps
		otpdao.deleteByUserAndOtpPurpose(user, purpose);
		
		//generate new otp
		Integer otp = ThreadLocalRandom.current().nextInt(100000, 1000000);
		
		OtpEntity otpEntity = new OtpEntity(null, user, otp, purpose, LocalDateTime.now().plusMinutes(10), false);
		
		otpdao.save(otpEntity);
		
		//send emial to user with new otp
		String subject = "Tikito password Reset Otp";
		
		String body = "Your OTP is: " + otp +
                	  "\n\nThis OTP is valid for 10 minutes." +
                      "\nDo not share this OTP with anyone.";
		
		emailService.sendEmail(email, subject ,body);
		
		
	}

	@Override
	public boolean verifyOtp(String email, Integer otp, OtpPurpose purpose) 
	{
		//get emial by emialId
        UserEntity user = userdao.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));

        //get the top unused otp
        Optional<OtpEntity> optionalOtp = otpdao.findTopByUserAndOtpPurposeAndUsedFalseOrderByCreatedAtDesc(user, purpose);

        // if otp is not found return false
        if (optionalOtp.isEmpty())
            return false;

        OtpEntity otpEntity = optionalOtp.get();

        // check if otp expired
        if (LocalDateTime.now().isAfter(otpEntity.getExpiryTime()))
            return false;

        return otpEntity.getOtp().equals(otp);
	}

	@Override
	public void invalidateOtp(String email, OtpPurpose purpose) 
	 {
        UserEntity user = userdao.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));

        //find unused otp
        Optional<OtpEntity> optionalOtp = otpdao.findTopByUserAndOtpPurposeAndUsedFalseOrderByCreatedAtDesc(user, purpose);

        //mark ir used
        if (optionalOtp.isPresent())
        {
            OtpEntity otpEntity = optionalOtp.get();

            otpEntity.setUsed(true);

            otpdao.save(otpEntity);
        }
    }
}
