package com.upchardwar.app.entity.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabReviewReplayRequest {

	
	Long replyId;
	Long  patientId;
	String description;
	Long reviewRatingId;
}
