package com.upchardwar.app.entity.payload;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.upchardwar.app.entity.lab.Booking;
import com.upchardwar.app.entity.lab.LabTest;
import com.upchardwar.app.entity.patient.Patient;
import com.upchardwar.app.entity.status.AppConstant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabInvoiceResponse {

	    private Long invoiceId;
   
	  


	    private Long totalAmount;
	    
	    private LocalDateTime invoiceGenerateDate;
	    
	    private String paymentMethod;
	    
	    private Long bookingId;
		private String invoiceStatus=AppConstant.INVOICE_STATUS_AWAITED;

		private String patientName;
		
		private String imageName;
		
		private Long patientId;
		
		private String testName;
		
		
}
