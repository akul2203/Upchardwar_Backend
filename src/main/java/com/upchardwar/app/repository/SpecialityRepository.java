package com.upchardwar.app.repository;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upchardwar.app.entity.doctor.Speciality;
import com.upchardwar.app.entity.payload.SpecialityRequest;
import com.upchardwar.app.entity.payload.SpecialityResponse;

import java.util.List;


public interface SpecialityRepository extends JpaRepository<Speciality, Long> {
	
   public Optional<Speciality> findBySpName(String spName);



}
