package com.upchardwar.app.entity.payload;


import java.util.ArrayList;
import java.util.List;

import com.upchardwar.app.entity.Location;
import com.upchardwar.app.entity.lab.LabDocument;
import com.upchardwar.app.entity.lab.LabReviewRating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabResponse {
	private Long id;

	private String labName;

	private String email;

	private String password;

	private Boolean isApproved = false;

	private String phone;

	private Location location;
	
    private String documentType;
	
	private String imageName;
	
	private String biography;
	
	private Boolean isDeleted=false;
	
	private List<LabReviewRating> labReviewRatings;
	
	private List<LabDocument> labDocuments=new ArrayList<>();
	
	private Long userId;
	
	private Double rating;

}
