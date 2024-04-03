package com.upchardwar.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upchardwar.app.entity.patient.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
	public Optional<Patient> findByPatientName(String patientName);

	public Optional<Patient> findByEmail(String email);

	
		
}
