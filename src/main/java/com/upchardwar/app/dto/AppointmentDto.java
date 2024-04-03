package com.upchardwar.app.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.upchardwar.app.entity.doctor.Doctor;
import com.upchardwar.app.entity.doctor.DoctorInvoice;
import com.upchardwar.app.entity.doctor.PatientAppointmentFile;
import com.upchardwar.app.entity.doctor.TimeSlote;
import com.upchardwar.app.entity.patient.Patient;
import com.upchardwar.app.entity.status.AppointmentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class AppointmentDto {

    private Long id;
    private LocalDate appointmentDate;
    private AppointmentStatus status;
    private String purpose;
    private TimeSlote timeSlot;
    private Patient patient;
    private Doctor doctor;
    private DoctorInvoice doctorInvoice;
    private PatientAppointmentFile patientAppointmentFile;
    
    
    
}
