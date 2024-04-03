package com.upchardwar.app.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.upchardwar.app.entity.doctor.Doctor;
import com.upchardwar.app.entity.patient.Patient;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class Conversation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Patient patient;

	@ManyToOne
	private Doctor doctor;

	private LocalDate date;

	private LocalTime startTime;

	private LocalTime endTime;

	@OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
	private List<Messages> messages;
}
