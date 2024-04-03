package com.upchardwar.app.entity.pharma;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Medicine {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String medicineName;
	
	private Float rates;
	
	@ManyToOne
	private Pharmacy pharmacy;
	
	@ManyToOne
	private MedicineOrder medicineOrder;
	
	@ManyToOne
	private PharmaRequest pharReq;

	
}
