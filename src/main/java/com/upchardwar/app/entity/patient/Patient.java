package com.upchardwar.app.entity.patient;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.upchardwar.app.entity.Conversation;
import com.upchardwar.app.entity.Messages;
import com.upchardwar.app.entity.doctor.Appointment;
import com.upchardwar.app.entity.doctor.DoctorInvoice;
import com.upchardwar.app.entity.doctor.PatientAppointmentFile;
import com.upchardwar.app.entity.doctor.Prescription;
import com.upchardwar.app.entity.lab.Booking;
import com.upchardwar.app.entity.lab.LabInvoice;
import com.upchardwar.app.entity.lab.LabPayment;
import com.upchardwar.app.entity.lab.LabReport;
import com.upchardwar.app.entity.lab.PatientFavoriteLab;
import com.upchardwar.app.entity.orders.payments;
import com.upchardwar.app.entity.pharma.PharmaRequest;
import com.upchardwar.app.entity.pharma.PharmaReviewRating;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String patientName;
	
	private String password;
	
	private String email;
	
	private String mobile;
	
	private Long age;
	
    private String address;
    
    
    private String city;
    
    private String country;
    
    private Float paidAmount; 
    
    private String zipcode;
    
    private String state;
    
    private String bloodGroup;
    
    
	public String documentType;
	
	public String imageName;
	

    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL)
    private List<payments> payments;
	
	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Booking> bookings;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="patient_id")
	@JsonIgnoreProperties(value="patient")
	private Set<LabReport> labReport;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="patient_id")
	@JsonIgnoreProperties(value="patient")
	private List<LabInvoice> labInvoices;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "patient")
	 @JsonIgnoreProperties(value = {"patient"})
	private List<DoctorInvoice> doctorInvoices;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "patient")
	private List<Appointment> appointments;
	

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "patient")
	private List<PatientFavoriteLab> favoriteLabs;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "patient")
	private List<PatientAppointmentFile> appointmentFiles;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "patient")
	private List<Prescription> Prescriptions;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"patient"})	
	private List<LabPayment> labPayments;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "patient")
	@JsonIgnore
	private List<PharmaRequest>  pharma_requests;
    
    @OneToMany(mappedBy = "patient" ,cascade = CascadeType.ALL)
	private List<PharmaReviewRating> pharmaReviewRatings;
    
//    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL)
//    private List<LabReviewRating> labReviewRatings;
    

    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL)
    private List<Messages> messages;
    
    @OneToMany(mappedBy = "patient" ,cascade = CascadeType.ALL)
    private List<Conversation> conversations;

	
    
    
}
