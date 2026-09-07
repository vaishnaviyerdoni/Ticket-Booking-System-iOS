package com.sunbeam.tikito.daos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbeam.tikito.entity.UserEntity;

public interface UserDao extends JpaRepository<UserEntity, Long>{
		Optional<UserEntity> findByEmail(String email);
		Optional<UserEntity> findByPhone(String phone);

}
