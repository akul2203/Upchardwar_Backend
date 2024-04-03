package com.upchardwar.app.entity.doctor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class TimeSlote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String startTime;

	private String endTime;

	private Boolean isBooked = false;

	private Boolean isDeleted = false;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="schedule_id")
	@JsonIgnoreProperties(value= {"timeSlots"})
	private Schedule schedule;

	@OneToOne(mappedBy = "timeslote")
	@JsonIgnore
	private Appointment appointment;

}
