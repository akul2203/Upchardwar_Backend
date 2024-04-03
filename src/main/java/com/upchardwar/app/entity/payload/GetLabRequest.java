package com.upchardwar.app.entity.payload;

import java.util.List;

import com.upchardwar.app.entity.Location;
import com.upchardwar.app.entity.lab.LabReviewRating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetLabRequest {
	private Long id;
    private String labName;
    private String email;
    private Boolean isApproved;
    private String phone;
    private String password;
    private String documentType;
    private String imageName;
    private String biography;
    private Boolean isDeleted;
    private Location location;
    private List<LabReviewRating> labReviewRatings;
}
