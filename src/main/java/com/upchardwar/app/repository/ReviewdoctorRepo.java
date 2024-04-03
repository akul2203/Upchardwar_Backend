package com.upchardwar.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upchardwar.app.entity.doctor.Review;

public interface ReviewdoctorRepo extends JpaRepository<Review,Long>{

	
    List<Review> findByDoctorId(Long doctorId);
    List<Review> findByPatientId(Long patientId);
}
