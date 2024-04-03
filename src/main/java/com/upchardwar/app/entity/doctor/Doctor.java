package com.upchardwar.app.entity.doctor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.upchardwar.app.entity.Conversation;
import com.upchardwar.app.entity.Messages;
import com.upchardwar.app.entity.status.AppConstant;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Doctor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	private String imageName;

	private String documentType;
	
 	@Column(unique = true)
	private Long userid;

	@ElementCollection
	@CollectionTable(name = "doctor_awards", joinColumns = @JoinColumn(name = "doctor_id"))
	@Column(name = "award")
	private Set<String> awards;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "speciality_id", unique = true)
	private Speciality speciality;

	@JsonIgnore
	@OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
	private List<DoctorQualification> qualifications = new ArrayList<>();

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "doctor_id")
	private List<DoctorDocument> doctorDocuments = new ArrayList<>();

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "doctor")
	private List<Schedule> schedules;

	@JsonIgnore
	@OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = { "doctor" })
	private List<DoctorInvoice> doctorInvoices;

	@JsonIgnore
	@OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
	private List<Appointment> appointments;

	@JsonIgnore
	@OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
	private List<PatientAppointmentFile> appointmentFiles;

	@JsonIgnore
	@OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
	private List<Prescription> prescriptions;

	@JsonIgnore
	@OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
	private List<Messages> messages;

	@JsonIgnore
	@OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
	private List<Conversation> conversations;

}
