package com.upchardwar.app.entity.payload;

import java.time.LocalDate;

import com.upchardwar.app.entity.doctor.Appointment;
import com.upchardwar.app.entity.doctor.Doctor;
import com.upchardwar.app.entity.patient.Patient;
import com.upchardwar.app.entity.status.AppConstant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorInvoiceRequest {

	private Long id;

	private LocalDate invoiceGenerateDate;

	private float amount;

	private String invoiceStatus=AppConstant.INVOICE_STATUS_AWAITED;

	private String paymentMethod;
	
	private Patient patient;
	
	private Doctor doctor;
	
	private Appointment appointment;
}
