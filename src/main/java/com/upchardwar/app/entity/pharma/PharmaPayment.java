package com.upchardwar.app.entity.pharma;

import java.time.LocalDate;
import java.time.LocalTime;

import com.upchardwar.app.entity.doctor.Prescription;
import com.upchardwar.app.entity.lab.Lab;
import com.upchardwar.app.entity.lab.LabTest;
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
public class PharmaPayment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String transactionId;
	
	private String type;
	
	private LocalDate date;
	
	private LocalTime time;
	
	@ManyToOne
	private Patient patient;
	
	@ManyToOne
	private Pharmacy pharmacy;
	
	@ManyToOne
	private Prescription prescription;
	
	@ManyToOne
	private LabTest labTest;
}
