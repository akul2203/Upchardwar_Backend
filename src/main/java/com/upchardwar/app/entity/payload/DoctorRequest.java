package com.upchardwar.app.entity.payload;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.upchardwar.app.entity.doctor.DoctorDocument;
import com.upchardwar.app.entity.doctor.DoctorQualification;
import com.upchardwar.app.entity.doctor.Speciality;
import com.upchardwar.app.entity.status.AppConstant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorRequest {
	private Long id;

	private String userName;

	private String name;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate DOB;

	private String gender;

	private String phone;

	private String password;

	private String email;

	private String biography;

	private String address;

	private String city;

	private String state;

	private String country;

	private String postalcode;

	private Integer rate;

	private String status = AppConstant.DOCTOR_STATUS_NEW;

	private Boolean isRejected = false;

	private LocalDate expierenceFrom;

	private LocalDate expierenceTo;
	
	public String documentType;
	
	private Long userid;
    
	private String imageName;
	
	private Set<String> awards;

	private Speciality speciality;

	private List<DoctorQualification> qualifications = new ArrayList<>();

	private List<DoctorDocument> doctorDocuments=new ArrayList<>();

}
