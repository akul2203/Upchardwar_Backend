package com.upchardwar.app.entity.payload;

import java.time.LocalDate;

import com.upchardwar.app.entity.status.AppointmentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequest {

	private Long id;
	private LocalDate appointmentDate;
	private AppointmentStatus status;
	private LocalDate bookingdate;
	private String purpose;
	private Long timeslotId; // Assuming you associate the appointment with a timeslot
	private PatientRequest patient;
    private DoctorRequest doctor;

}
