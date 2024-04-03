package com.upchardwar.app.entity.doctor;

import java.util.ArrayList;

import java.util.List;

import jakarta.persistence.Id;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String text;
	
	private int rating;

	private Long doctorId;

	private Long patientId;

	@OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
	private List<Reply> replies = new ArrayList<>();

}
