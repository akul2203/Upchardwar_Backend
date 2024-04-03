package com.upchardwar.app.entity.payload;

import com.upchardwar.app.entity.lab.Lab;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLabTestResponse {
     private Long id;
	
	 private String testName;
	
	 private String description;
	 
	 private Long rates; 
	 
	 private Boolean availability;
	 
	 private Long labId;
}
