package com.upchardwar.app.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.upchardwar.app.entity.doctor.Speciality;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientAppointmentDto {

	private Long id;
	private String patientName;
    private LocalDate appointmentDate;
    private String purpose;
    private Float PaidAmount;
    private String status;
    private LocalTime appointmentTime; 
   
   // private  Doctor doctor;
    
    private Long dId;
    private String email;
    private String mobile;
    
    private String doctorName;
    private String doctorSpeciality;
}
