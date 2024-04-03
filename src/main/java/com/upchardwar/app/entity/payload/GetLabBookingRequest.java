package com.upchardwar.app.entity.payload;

import java.time.LocalDate;

import com.upchardwar.app.entity.lab.LabInvoice;
import com.upchardwar.app.entity.lab.LabTest;
import com.upchardwar.app.entity.patient.Patient;
import com.upchardwar.app.entity.status.LabTestStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.StandardException;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetLabBookingRequest {

	 private Long bookingId;
	    private Long patientId;
	    private String patientName;
	    private Long labTestId;
	    private String testName;
	    private LocalDate bookingDate;
	    private Long amount;
	    private String purpose;
	    private Long labId;
	    private LabTestStatus status;
	    private String imageName;
	    private String labName;
	    
}
