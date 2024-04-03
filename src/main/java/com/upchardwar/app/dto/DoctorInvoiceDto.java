package com.upchardwar.app.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoctorInvoiceDto {
	 private LocalDate invoiceGenerateDate;
	    private float amount;
	    private String invoiceStatus;
	    private String paymentMethod;
	    private Long doctorId;
	    private Long patientId;
	    private Long appointmentId;
}
