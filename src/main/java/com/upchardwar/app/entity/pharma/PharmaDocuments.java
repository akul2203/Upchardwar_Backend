package com.upchardwar.app.entity.pharma;

import java.time.LocalDate;
import java.util.List;

import com.upchardwar.app.entity.status.PharmaStatus;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class PharmaDocuments {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String docType;
	
	private String docOriginalName;
	
	private String filename;
	
	private LocalDate uploadDate;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<PharmaStatus> pharmaStatusts ;
	
	@ManyToOne
	private Pharmacy pharmacy;
	
	
	
}
