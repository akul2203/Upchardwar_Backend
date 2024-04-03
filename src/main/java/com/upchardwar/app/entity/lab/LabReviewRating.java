package com.upchardwar.app.entity.lab;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.upchardwar.app.entity.patient.Patient;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class LabReviewRating {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonIgnore
	@OneToOne
	private Patient patient;

	private Integer rating;

	private LocalDateTime createTime;

	@Column(columnDefinition = "longtext")
	private String description;

	@OneToMany(mappedBy = "reviewRating", cascade = CascadeType.ALL)
	private List<LabReviewReply> replies = new ArrayList<>();

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "lab_id")
	private Lab lab;

}
