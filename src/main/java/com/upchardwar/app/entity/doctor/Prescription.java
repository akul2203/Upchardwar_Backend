package com.upchardwar.app.entity.doctor;

import java.util.List;

import com.upchardwar.app.entity.patient.Patient;
import com.upchardwar.app.entity.pharma.PharmaPayment;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Prescription {

	@Id
	private Long id;
	
	private List<String> Diagnosis;
	
	private String numofTime;
	
	private String numofDays;
	
	private String remark;
	
	private String status;
	
	@OneToMany(mappedBy = "prescription",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<PharmaPayment> pharmaPayments;
	
	@ManyToOne
	private Patient patient;
	
	@ManyToOne
	private Doctor doctor;
	
	
}
