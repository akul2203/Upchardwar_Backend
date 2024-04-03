package com.upchardwar.app.entity.payload;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.upchardwar.app.entity.doctor.DoctorDocument;
import com.upchardwar.app.entity.doctor.DoctorQualification;
import com.upchardwar.app.entity.doctor.Speciality;
import com.upchardwar.app.entity.status.AppConstant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponse {
	private Long id;

	private String userName;

	private String name;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate DOB;

	private String gender;

	private String phone;


	private String email;

	private String biography;

	private String address;

	private String city;

	private String state;

	private String country;

	private String postalcode;

	private Integer rate;

	private Long userid;
	
	private String status = AppConstant.DOCTOR_STATUS_NEW;

	private Boolean isRejected = false;

	private LocalDate expierenceFrom;

	private LocalDate expierenceTo;

	private Set<String> awards;
	
	public String documentType;
	
	public String imageName;

	
	private Speciality speciality;
	@JsonIgnore
	private List<DoctorQualification> qualifications = new ArrayList<>();

	@JsonIgnore
	private List<DoctorDocument> doctorDocuments=new ArrayList<>();
}
