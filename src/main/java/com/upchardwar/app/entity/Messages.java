package com.upchardwar.app.entity;

import java.time.LocalDateTime;

import com.upchardwar.app.entity.doctor.Doctor;
import com.upchardwar.app.entity.patient.Patient;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Messages {

	@Id

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mId;

	private String msgContext;

	private LocalDateTime timestamp;

	@ManyToOne
	private Patient patient;

	@ManyToOne
	private Doctor doctor;

	@ManyToOne
	private Conversation conversation;

}
