package com.sunbeam.tikito.serviceimpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sunbeam.tikito.daos.UserDao;
import com.sunbeam.tikito.dto.UserDto;
import com.sunbeam.tikito.entity.UserEntity;
import com.sunbeam.tikito.services.AdminService;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class AdminServiceImpl implements AdminService{	
	 private final UserDao userDao;
	    private final ModelMapper modelMapper;
	    private final PasswordEncoder passwordEncoder;

	    public AdminServiceImpl(UserDao userDao,
	                            ModelMapper modelMapper,
	                            PasswordEncoder passwordEncoder) {
	        this.userDao = userDao;
	        this.modelMapper = modelMapper;
	        this.passwordEncoder = passwordEncoder;
	    }
	    @Override
	    public UserDto registerAdmin(UserDto dto) {

	        if (userDao.findByEmail(dto.getEmail()).isPresent()) {
	            throw new RuntimeException("Email Already Exists");
	        }

	        UserEntity admin = modelMapper.map(dto, UserEntity.class);

	        admin.setPassword(passwordEncoder.encode(dto.getPassword()));
	        admin.setRole("ROLE_ADMIN");

	        UserEntity savedAdmin = userDao.save(admin);

	        UserDto response = modelMapper.map(savedAdmin, UserDto.class);
	        response.setPassword(null);

	        return response;
	    }
		
		
//	@Override
//	public UserDto loginAdmin(UserDto dto) {
//		UserEntity admin = userDao.findByEmail(dto.getEmail()).orElseThrow(() ->
//		new RuntimeException("Invalid Email"));
//
//        if(!admin.getPassword().equals(dto.getPassword()))
//            throw new RuntimeException("Invalid Password");
//
//        if(!admin.getRole().equals("Admin"))
//            throw new RuntimeException("Access Denied");
//
//        UserDto response =modelMapper.map(admin,UserDto.class);
//        response.setPassword(null);
//        return response;
//    }

}
