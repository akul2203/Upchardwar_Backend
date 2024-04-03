package com.upchardwar.app.entity.payload;

import java.time.LocalDate;

import com.upchardwar.app.entity.status.AppConstant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorInvoiceResponse {

	private Long id;

	private LocalDate invoiceGenerateDate;

	private float amount;

	private String invoiceStatus=AppConstant.INVOICE_STATUS_AWAITED;

	private String paymentMethod;
	
	private String patientName;
	
	private String imageName;
	
	private Long patientId;
	
	private TimeSlotRequest timeslote;
	
     
}
