package com.upchardwar.app.entity.payload;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequest {

	private Long id;

	private String patientName;

	private String password;

	private String imageName;

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

}
