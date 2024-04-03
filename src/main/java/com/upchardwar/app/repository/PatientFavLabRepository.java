package com.upchardwar.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upchardwar.app.entity.lab.PatientFavoriteLab;

public interface PatientFavLabRepository extends JpaRepository<PatientFavoriteLab, Long> {

	Page<PatientFavoriteLab> findByPatientId(Long patientId, Pageable pageable);

	Optional<PatientFavoriteLab> findByPatientIdAndLabId(Long patientId, Long labId);

}
