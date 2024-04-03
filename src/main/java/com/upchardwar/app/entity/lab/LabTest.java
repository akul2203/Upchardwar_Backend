package com.upchardwar.app.entity.lab;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.upchardwar.app.entity.patient.Patient;
import com.upchardwar.app.entity.pharma.PharmaPayment;
import com.upchardwar.app.entity.status.AppConstant;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LabTest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String testName;
	
    private Integer ratings;
    
    private String description;
    
    private Boolean isDelete=false;
	
	private Long rates; 
	
	private Boolean availability;
	
	
	@ManyToOne
    @JoinColumn(name = "lab_id")
    private Lab lab;


	@JsonIgnore
    @OneToMany(mappedBy = "labTest", cascade = CascadeType.ALL)
    private List<Booking> bookings;
    
	@JsonIgnore
    @OneToMany(mappedBy = "labTest", cascade = CascadeType.ALL)
    private List<LabReport> labReports;
    
	@JsonIgnore
    @OneToMany(mappedBy = "labTest", cascade = CascadeType.ALL)
    private List<LabInvoice> invoices;

	
	
}
