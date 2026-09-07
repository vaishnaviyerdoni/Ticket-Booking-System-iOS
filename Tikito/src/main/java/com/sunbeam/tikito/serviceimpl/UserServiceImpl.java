package com.sunbeam.tikito.serviceimpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
import com.sunbeam.tikito.daos.UserDao;
import com.sunbeam.tikito.dto.UserDto;
import com.sunbeam.tikito.entity.UserEntity;
import com.sunbeam.tikito.enums.OtpPurpose;
import com.sunbeam.tikito.exceptions.UserNotFoundException;
import com.sunbeam.tikito.services.OtpService;
import com.sunbeam.tikito.services.UserService;
import jakarta.transaction.Transactional;


@Service
@Transactional
public class UserServiceImpl implements UserService {

	  private final UserDao userDao;
	    private final ModelMapper modelMapper;
	    private final PasswordEncoder passwordEncoder;
	    private final OtpService otpService;

	    @Autowired
	    public UserServiceImpl(UserDao userDao,
	                           ModelMapper modelMapper,
	                           PasswordEncoder passwordEncoder,
	                           OtpService otpService) {
	        this.userDao = userDao;
	        this.modelMapper = modelMapper;
	        this.passwordEncoder = passwordEncoder;
	        this.otpService = otpService;
	    }

    @Override
    public UserDto register(UserDto dto) {
    	
    	

        if (userDao.findByEmail(dto.getEmail()).isPresent())
            throw new RuntimeException("Email already exists");

        if (userDao.findByPhone(dto.getPhone()).isPresent())
            throw new RuntimeException("Phone already exists");

        UserEntity user = modelMapper.map(dto, UserEntity.class);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("ROLE_USER");

        UserEntity savedUser = userDao.save(user);

        UserDto response = modelMapper.map(savedUser, UserDto.class);

        response.setPassword(null);
        response.setOldPassword(null);
        response.setNewPassword(null);

        return response;
    }

    @Override
    public UserDto getProfile(Long userId) {

        UserEntity user = userDao.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        UserDto response = modelMapper.map(user, UserDto.class);
        
        if (response.getImageName() != null &&
        	    !response.getImageName().isBlank()) {

        	    response.setImageName("/profiles/" + response.getImageName());
        	}

        response.setPassword(null);
        response.setOldPassword(null);
        response.setNewPassword(null);

        return response;
    }

    @Override
    public String updatePassword(Long userId, UserDto dto) {

        UserEntity user = userDao.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        if (!passwordEncoder.matches(dto.getOldPassword(),
                                     user.getPassword()))
            throw new RuntimeException("Old Password is Incorrect");

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));

        userDao.save(user);

        return "Password Updated Successfully";
    }

  //modified for email otp service
    @Override
    public String forgotPassword(UserDto dto) {

    	  otpService.generateAndSendOtp(dto.getEmail(), OtpPurpose.PASSWORD_RESET);

    	    return "OTP Sent Successfully";
    }
    
    //added for email otp service
    @Override
    public String resetPassword(UserDto dto)
    {
    	boolean isVerified = otpService.verifyOtp(dto.getEmail(), dto.getOtp(), OtpPurpose.PASSWORD_RESET);
    	
    	if(!isVerified)
    	{
    		throw new RuntimeException("Invalid or expired otp");
    	}
    	
    	UserEntity user = userDao.findByEmail(dto.getEmail()).orElseThrow(() -> new UserNotFoundException("user not found"));
    	
    	user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
    	userDao.save(user);
    	
    	otpService.invalidateOtp(dto.getEmail(), OtpPurpose.PASSWORD_RESET);
    	
    	return "Password reset successful";
    }

    @Override
    public String deleteAccount(Long userId) {

        UserEntity user = userDao.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        userDao.delete(user);

        return "Account Deleted Successfully";
    }
    
    @Override
    public String updateProfileImage(Long userId, UserDto dto) {

        UserEntity user = userDao.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        user.setImageName(dto.getImageName());

        userDao.save(user);

        return "Profile Image Updated Successfully";
    }
    
    //update profile info
    @Override
    public String updateProfile(Long userId, UserDto dto) {

        UserEntity user = userDao.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        if(dto.getFirstName()!=null)
            user.setFirstName(dto.getFirstName());

        if(dto.getLastName()!=null)
            user.setLastName(dto.getLastName());

        if(dto.getEmail()!=null &&
        		   !dto.getEmail().equals(user.getEmail())){

        		    if(userDao.findByEmail(dto.getEmail()).isPresent())
        		        throw new RuntimeException("Email already exists");

        		    user.setEmail(dto.getEmail());
        		}

        if(dto.getPhone()!=null &&
        		   !dto.getPhone().equals(user.getPhone())){

        		    if(userDao.findByPhone(dto.getPhone()).isPresent())
        		        throw new RuntimeException("Phone already exists");

        		    user.setPhone(dto.getPhone());
        		}

        userDao.save(user);

        return "Profile Updated Successfully";
    }

}





