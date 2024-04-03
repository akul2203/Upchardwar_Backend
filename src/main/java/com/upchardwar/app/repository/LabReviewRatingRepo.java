package com.upchardwar.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upchardwar.app.entity.lab.Lab;
import com.upchardwar.app.entity.lab.LabReviewRating;
import com.upchardwar.app.entity.patient.Patient;

public interface LabReviewRatingRepo extends JpaRepository<LabReviewRating, Long> {

	LabReviewRating findByLabAndPatient(Lab lab, Patient patient);

	List<LabReviewRating> findByLabId(long labId);

Optional<LabReviewRating> findByIdAndPatientId(Long id, Long id2);

Optional<LabReviewRating> findByIdAndLabId(Long id, Long id2);

List<LabReviewRating> findAllByLabId(Long labId);

}
