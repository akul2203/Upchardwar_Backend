package com.upchardwar.app.entity.pharma;

import java.time.LocalDate;
import java.util.List;

import com.upchardwar.app.entity.patient.Patient;
import com.upchardwar.app.entity.status.PharmaStatus;

import jakarta.persistence.CascadeType;
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
public class MedicineOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String medicineName;

	private Long medicineQuantity;

	private LocalDate orderdate;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<PharmaStatus> pharmaStatusts ;

	@OneToOne
	private Patient patient;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "medicineOrder")
	private List<Medicine> medicines;

	@ManyToOne
	private Pharmacy pharmacy;
}
