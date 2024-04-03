package com.upchardwar.app.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@NoArgsConstructor
public class TodaysAppointmentDto {
	    private Long id;
	    private String patientName;
	    private LocalDate appointmentDate;
	    private String purpose;
	    private Long pid;
	    private Float paidAmount;
	    private String status;
	    
	    
	    public TodaysAppointmentDto(
	            Long id,
	            String patientName,
	            LocalDate appointmentDate,
	            String purpose,
	            Long patientId,
	            Float paidAmount,
	            String status
	    ) {
	        this.id = id;
	        this.patientName = patientName;
	        this.appointmentDate = appointmentDate;
	        this.purpose = purpose;
	        this.pid= patientId;
	        this.paidAmount = paidAmount;
	        this.status = status;
	    }
}
