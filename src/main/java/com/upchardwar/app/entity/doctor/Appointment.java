package com.upchardwar.app.entity.doctor;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.upchardwar.app.entity.patient.Patient;
import com.upchardwar.app.entity.status.AppointmentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Appointment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate bookingdate;
	
	private LocalDate appointmentDate;

//	private LocalTime appointmentTime;

	@Enumerated(EnumType.STRING)
	private AppointmentStatus status;

	private String purpose;

	@OneToOne
	private TimeSlote timeslote;

	@ManyToOne
	private Patient patient;

	@ManyToOne
	private Doctor doctor;

	@ManyToOne
	@JsonIgnore
	private PatientAppointmentFile patientAppointmentFile;

}
