package com.upchardwar.app.entity.pharma;

import java.util.List;

import com.upchardwar.app.entity.Location;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pharmacy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String pharmaName;
	
	private String email;
	
	private Boolean isApproved=false;
	
	private String password;
	
	private String phone;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Location location;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "pharmacy")
	private List<Medicine> medicines;
	
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "pharmacy")
	private List<MedicineInvoice> medicineInvoices;
	
	@OneToMany(mappedBy = "pharmacy",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<PharmaPayment> pharmaPayments;
	
	@OneToMany(mappedBy = "pharmacy",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<PharmaReviewRating> pharmaReviewRatings;
	
	@OneToMany(mappedBy = "pharmacy")
	private List<MedicineOrder> medicineOrders;
	
	@OneToMany(mappedBy = "pharmacy")
	private List<PharmaDocuments> documents;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "pharmacy")
	private List<PharmaRequest> pharma_requests;
}
