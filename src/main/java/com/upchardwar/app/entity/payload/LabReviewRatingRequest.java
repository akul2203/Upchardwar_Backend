package com.upchardwar.app.entity.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabReviewRatingRequest {
     Long labReviewId;
	Integer rating;
	Long labId;
	Long  patientId;
	String description;
}
