package com.upchardwar.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upchardwar.app.entity.Varification;

public interface VarificationRepository extends JpaRepository<Varification, Long> {


	Varification findByOtp(String otp);

	Optional<Varification> findByEmailAndIsActive(String email, boolean b);

	Optional<Varification> findByEmail(String email);

	Optional<Varification> findByEmailAndOtp(String email, String otp);

	 
	
		
	

}
