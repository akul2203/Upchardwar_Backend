package com.upchardwar.app.entity.pharma;

import com.upchardwar.app.entity.doctor.Doctor;
import com.upchardwar.app.entity.patient.Patient;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PharmaReviewRating {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Patient patient;

	@ManyToOne
	private Doctor doctor;

	
	private Integer rating;

	private String review;
	
	@ManyToOne
	private Pharmacy pharmacy;

}
