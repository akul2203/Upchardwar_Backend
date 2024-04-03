package com.upchardwar.app.entity.lab;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.upchardwar.app.entity.patient.Patient;
import com.upchardwar.app.entity.status.LabTestStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyEnumerated;
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
public class Booking {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long bookingId;

	    @ManyToOne
	    @JoinColumn(name = "patient_id")
	    private Patient patient;

	    @ManyToOne
	    @JoinColumn(name = "test_id")
	    private LabTest labTest;

	    private LocalDate bookingDate ;
	    
	    private Long amount;
	    
	    private String purpose;
	    
	    
	    private Long labId;
	    
	    private String labName;

	    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
	    private LabReport labTestReport;

	    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
	    private LabPayment payment;

	    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
	    private LabInvoice labInvoice;
	    

	    
//	   private Map<LabTestStatus,LocalDateTime> labStatusHistory = new HashMap<>();
	   
	    @MapKeyEnumerated(EnumType.STRING)
	    private LabTestStatus status = LabTestStatus.PENDING;
}
