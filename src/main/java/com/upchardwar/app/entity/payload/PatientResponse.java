package com.upchardwar.app.entity.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponse {
    private Long id;
	
	private String patientName;
	
	private String password;
	
	
	private String address;
	
	private String email;
	
	private String mobile;
	
	private String age;
	
	 private String city;
	    
	    private String country;
	    
	    private String zipcode;
	    
	    private String state;
	    
	    private String bloodGroup;
	    
	    public String documentType;
		
		public String imageName;
}
