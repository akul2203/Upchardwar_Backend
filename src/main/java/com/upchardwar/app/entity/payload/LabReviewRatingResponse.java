package com.upchardwar.app.entity.payload;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.upchardwar.app.entity.lab.Lab;
import com.upchardwar.app.entity.patient.Patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LabReviewRatingResponse {
	 Long id;

	Integer rating;
	Long labId;
	Long patientId;
	String description;

	String patientName;

	String imageName;
	List<LabReviewReplayResponse> replyResponse;
}
