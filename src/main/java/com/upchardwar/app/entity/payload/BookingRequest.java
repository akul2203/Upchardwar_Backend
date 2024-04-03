package com.upchardwar.app.entity.payload;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.upchardwar.app.entity.lab.Lab;
import com.upchardwar.app.entity.lab.LabInvoice;
import com.upchardwar.app.entity.lab.LabTest;
import com.upchardwar.app.entity.patient.Patient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {

	private Patient patient;
    private LabTest labTest;
    private LocalDate bookingDate;
    
    private Long amount;
    
    private String purpose;
    
    private Long labId;
    
    private String labName;
    
   
}
