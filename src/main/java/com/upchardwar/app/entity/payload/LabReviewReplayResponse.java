package com.upchardwar.app.entity.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabReviewReplayResponse {
	 Long id;

	Long patientId;
	String description;
	Long reviewRatingId;
	String patientName;
	String imageName;
}
