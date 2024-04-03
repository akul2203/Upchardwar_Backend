package com.upchardwar.app.entity.payload;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upchardwar.app.entity.doctor.Appointment;
import com.upchardwar.app.entity.doctor.Doctor;
import com.upchardwar.app.entity.lab.Booking;
import com.upchardwar.app.entity.lab.LabTest;
import com.upchardwar.app.entity.patient.Patient;
import com.upchardwar.app.entity.status.AppConstant;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LabInvoiceRequest {

	  private Long invoiceId;

	    
	    private Booking booking;

	    
	    private LabTest labTest;

	    private Long totalAmount;
	    
	    private Long labId;
	    
	    private LocalDateTime invoiceGenerateDate;
	    
	    private String paymentMethod;
	    
	    private Patient patient;
	    
        
}
