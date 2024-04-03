package com.upchardwar.app.services.lab;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.upchardwar.app.entity.payload.DoctorInvoiceRequest;
import com.upchardwar.app.entity.payload.LabInvoiceRequest;
import com.upchardwar.app.entity.payload.LabInvoiceResponse;

public interface ILabInvoiceService {

	public ResponseEntity<?> createLabInvoice(LabInvoiceRequest request)  ;

	

	Page<LabInvoiceResponse> getInvoiceByLabId(Long labId, Pageable pageable);
	    
}
