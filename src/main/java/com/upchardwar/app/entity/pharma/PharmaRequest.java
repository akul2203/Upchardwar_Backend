package com.upchardwar.app.entity.pharma;

import java.util.List;

import com.upchardwar.app.entity.Location;
import com.upchardwar.app.entity.patient.Patient;
import com.upchardwar.app.entity.status.PharmaStatus;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PharmaRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reqId;
	
	private Float totalBill;
	
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<PharmaStatus> pharmaStatusts ;
	
	@OneToMany(mappedBy = "pharReq")
	private List<Medicine> medicines;
	
	@ManyToOne
	private Patient patient;
	
	private String location;  //location get at order time not user location
	@ManyToOne
	private Pharmacy pharmacy;
	
	
//LIST OF MEDICINE
//PATIENT MANYTOONE
//LOCATION
//PHARMA BIND MANYTONE
//TOTALBILL
//STATUS
}
